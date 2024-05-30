package com.cgomezq.hackernews.news.data.repositories

import com.cgomezq.hackernews.news.data.mappings.PostMappings
import com.cgomezq.hackernews.news.data.models.NewsBody
import com.cgomezq.hackernews.news.data.models.PostLocalModel
import com.cgomezq.hackernews.news.data.models.PostModel
import com.cgomezq.hackernews.news.data.sources.NewsService
import com.cgomezq.hackernews.news.data.sources.PostDao
import com.cgomezq.hackernews.news.domain.entities.Post
import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.given
import org.mockito.kotlin.stub
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.time.LocalDateTime

class ActualPostRepositoryTest {

    @Mock
    private lateinit var newsService: NewsService

    @Mock
    private lateinit var postDao: PostDao

    @Mock
    private lateinit var mappings: PostMappings

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        postDao.stub {
            onBlocking { getAll() }.doReturn(emptyList())
        }
        newsService.stub {
            onBlocking { getNews("mobile") }.doReturn(
                Response.success(NewsBody(hits = emptyList()))
            )
        }
    }

    @Test(expected = IllegalStateException::class)
    fun getPosts_givenServiceError_shouldThrowException() = runTest {
        val repository = ActualPostRepository(
            isNetworkAvailable = { true }, newsService, postDao, mappings
        )

        newsService.stub {
            onBlocking { getNews("mobile") }.doReturn(
                Response.error(
                    500,
                    ResponseBody.create(MediaType.parse("application/json"), "")
                )
            )
        }

        given(repository.getPosts()).willThrow(IllegalStateException())
    }

    @Test
    fun getPosts_givenNetworkNotAvailable_shouldReturnDataFromDatabase() = runTest {
        val repository = ActualPostRepository(
            isNetworkAvailable = { false }, newsService, postDao, mappings
        )

        val posts = repository.getPosts()

        verify(newsService, times(0)).getNews(query = "")
        verify(postDao, times(1)).getAll()
        assertEquals(emptyList<Post>(), posts)
    }

    @Test
    fun getPosts_givenNetworkAvailable_shouldSaveData() = runTest {
        val repository = ActualPostRepository(
            isNetworkAvailable = { true }, newsService, postDao, mappings
        )

        val posts = repository.getPosts()

        verify(newsService, times(1)).getNews(query = "mobile")
        verify(postDao, times(1)).insertAll(
            *emptyList<PostLocalModel>().toTypedArray()
        )
        assertEquals(listOf<Post>(), posts)
    }

    @Test
    fun getPosts_givenNetworkAvailable_shouldMapValues() = runTest {
        val repository = ActualPostRepository(
            isNetworkAvailable = { true }, newsService, postDao, mappings
        )

        val posts = repository.getPosts()

        with(mappings) {
            whenever(listOf<PostModel>().toPostsLocalModel()).doReturn(emptyList())
            whenever(listOf<PostLocalModel>().toPosts()).doReturn(emptyList())
        }
        assertEquals(listOf<Post>(), posts)
    }

    @Test
    fun deletePost_givenPost_shouldUpdatePostStateInDatabase() = runTest {
        val repository = ActualPostRepository(
            isNetworkAvailable = { false }, newsService, postDao, mappings
        )
        val fakePostLocalModel = PostLocalModel(
            id = "123",
            storyTitle = "",
            isDeleted = false,
            author = "",
            storyUrl = "",
            createdAt = 1
        )
        postDao.stub {
            onBlocking { getPost(id = "123") }.doReturn(
                fakePostLocalModel
            )
        }

        repository.deletePost(
            Post(
                id = 123,
                title = "",
                date = LocalDateTime.now(),
                authorName = "",
                link = ""
            )
        )

        verify(postDao, times(1)).updatePost(
            fakePostLocalModel.copy(isDeleted = true)
        )
    }
}