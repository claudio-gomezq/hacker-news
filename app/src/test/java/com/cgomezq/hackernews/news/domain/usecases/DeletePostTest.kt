package com.cgomezq.hackernews.news.domain.usecases

import com.cgomezq.hackernews.news.domain.entities.Post
import com.cgomezq.hackernews.news.domain.repositories.PostRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class DeletePostTest {

    @Mock
    private lateinit var repository: PostRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getPost_shouldCallDeletePostRepositoryMethod() = runTest {
        val getPosts = DeletePost(repository)
        val fakePost = Post(
            id = 0,
            title = "",
            date = LocalDateTime.now(),
            authorName = "",
            link = ""
        )
        getPosts(
            post = fakePost
        )
        verify(repository, times(1)).deletePost(fakePost)
    }
}