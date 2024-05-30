package com.cgomezq.hackernews.news.presentation.logic

import com.cgomezq.hackernews.news.domain.entities.Post
import com.cgomezq.hackernews.news.domain.usecases.DeletePost
import com.cgomezq.hackernews.news.domain.usecases.GetPosts
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.stub
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class NewsViewModelTest {

    @Mock
    private lateinit var getPosts: GetPosts

    @Mock
    private lateinit var deletePost: DeletePost

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_atStart_shouldStartWithLoadingState() = runTest {
        val viewModel = NewsViewModel(getPosts, deletePost)

        assertEquals(NewsState.Loading, viewModel.uiState.value)
    }

    @Test
    fun uiState_givenGetNewsIntent_shouldHaveShowingNewsState() = runTest {
        val viewModel = NewsViewModel(getPosts, deletePost)

        getPosts.stub {
            onBlocking { invoke() }.doReturn(emptyList())
        }

        viewModel.emitIntent(NewsIntent.GetNews)

        assertEquals(
            NewsState.ShowingNews(
                isRefreshing = false,
                posts = emptyList()
            ),
            viewModel.uiState.first()
        )
    }

    @Test
    fun uiState_givenGetNewsIntentAndError_shouldHaveErrorState() = runTest {
        val viewModel = NewsViewModel(getPosts, deletePost)
        getPosts.stub {
            onBlocking { invoke() }.doThrow(IllegalStateException())
        }

        viewModel.emitIntent(NewsIntent.GetNews)

        assertEquals(
            NewsState.ShowingError,
            viewModel.uiState.first()
        )
    }

    @Test
    fun uiState_givenRefreshNewsIntent_shouldRefreshList() = runTest {
        val viewModel = NewsViewModel(getPosts, deletePost)
        getPosts.stub {
            onBlocking { invoke() }.doReturn(emptyList())
        }
        backgroundScope.launch(dispatcher) {
            viewModel.uiState.collect{}
        }
        viewModel.emitIntent(NewsIntent.RefreshNews)
        verify(getPosts, times(2)).invoke()
    }

    @Test
    fun uiState_givenRefreshNewsIntentAndError_shouldEmitErrorEffect() = runTest {
        val viewModel = NewsViewModel(getPosts, deletePost)
        val uiEffectResultList = mutableListOf<NewsEffect>()
        getPosts.stub {
            onBlocking { invoke() }.doReturn(emptyList())
        }
        backgroundScope.launch(dispatcher) {
            viewModel.uiState.collect {}
        }
        backgroundScope.launch(dispatcher) {
            viewModel.uiEffect.toList(uiEffectResultList)
        }

        getPosts.stub {
            onBlocking { invoke() }.doThrow(IllegalStateException())
        }
        viewModel.emitIntent(NewsIntent.RefreshNews)

        assertEquals(NewsEffect.RefreshingError, uiEffectResultList.first())
    }

    @Test
    fun uiState_givenDeletePostIntent_shouldRemovePost() = runTest {
        val viewModel = NewsViewModel(getPosts, deletePost)
        val fakePost = Post(
            id = 123,
            title = "",
            date = LocalDateTime.now(),
            authorName = "",
            link = ""
        )
        getPosts.stub {
            onBlocking { invoke() }.doReturn(listOf(fakePost))
        }
        backgroundScope.launch(dispatcher) {
            viewModel.uiState.collect {}
        }

        viewModel.emitIntent(NewsIntent.DeletePost(fakePost))

        verify(deletePost, times(1)).invoke(fakePost)
        assertEquals(
            NewsState.ShowingNews(
                isRefreshing = false,
                posts = emptyList()
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun uiState_givenDeletePostIntentAndError_shouldEmitErrorEffect() = runTest {
        val viewModel = NewsViewModel(getPosts, deletePost)
        val uiEffectResultList = mutableListOf<NewsEffect>()
        val fakePost = Post(
            id = 123,
            title = "",
            date = LocalDateTime.now(),
            authorName = "",
            link = ""
        )
        getPosts.stub {
            onBlocking { invoke() }.doReturn(emptyList())
        }
        deletePost.stub {
            onBlocking { invoke(fakePost) }.doThrow(IllegalStateException())
        }

        backgroundScope.launch(dispatcher) {
            viewModel.uiState.collect {}
        }
        backgroundScope.launch(dispatcher) {
            viewModel.uiEffect.toList(uiEffectResultList)
        }

        viewModel.emitIntent(NewsIntent.DeletePost(fakePost))

        assertEquals(NewsEffect.DeletingError, uiEffectResultList.first())
    }
}