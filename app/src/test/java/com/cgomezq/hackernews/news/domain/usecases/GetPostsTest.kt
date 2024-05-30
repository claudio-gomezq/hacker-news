package com.cgomezq.hackernews.news.domain.usecases

import com.cgomezq.hackernews.news.domain.entities.Post
import com.cgomezq.hackernews.news.domain.repositories.PostRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class GetPostsTest {

    @Mock
    private lateinit var repository: PostRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    private fun createFakePost(date: LocalDateTime) = Post(
        id = 0,
        title = "",
        date = date,
        authorName = "",
        link = ""
    )

    @Test
    fun getPost_shouldReturnListInDescendingOrderByDate() = runTest {
        val now = LocalDateTime.now()
        val fakePostList = listOf(
            createFakePost(date = now.minusHours(10)),
            createFakePost(date = now.minusHours(4)),
            createFakePost(date = now.minusHours(7)),
        )
        val orderedFakePostList = listOf(
            createFakePost(date = now.minusHours(4)),
            createFakePost(date = now.minusHours(7)),
            createFakePost(date = now.minusHours(10)),
        )
        repository.stub {
            onBlocking { getPosts() }.doReturn(fakePostList)
        }

        val getPosts = GetPosts(repository)
        val posts = getPosts()
        Assert.assertEquals(orderedFakePostList, posts)
    }
    @Test
    fun getPost_shouldCallGetPostRepositoryMethod() = runTest {
        repository.stub {
            onBlocking { getPosts() }.doReturn(listOf())
        }
        val getPosts = GetPosts(repository)
        getPosts()
        verify(repository, times(1)).getPosts()
    }
}