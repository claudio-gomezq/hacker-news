package com.cgomezq.hackernews.news.data.repositories

import com.cgomezq.hackernews.common.network.getBodyOrError
import com.cgomezq.hackernews.common.network.qualifiers.NetworkAvailable
import com.cgomezq.hackernews.news.data.mappings.PostMappings
import com.cgomezq.hackernews.news.data.sources.NewsService
import com.cgomezq.hackernews.news.data.sources.PostDao
import com.cgomezq.hackernews.news.domain.entities.Post
import com.cgomezq.hackernews.news.domain.repositories.PostRepository
import javax.inject.Inject
import javax.inject.Provider

class ActualPostRepository @Inject constructor(
    @NetworkAvailable private val isNetworkAvailable: Provider<Boolean>,
    private val service: NewsService,
    private val postDao: PostDao,
    private val mappings: PostMappings
) : PostRepository {

    override suspend fun getPosts(): List<Post> {
        if (isNetworkAvailable.get()) {
            val response = service.getNews(query = "mobile")
            val body = response.getBodyOrError(message = "Error getting response")
            val localPost = with(mappings) {
                body.hits.toPostsLocalModel()
            }
            postDao.insertAll(*localPost.toTypedArray())
        }
        val postDao = postDao.getAll()
        return with(mappings) {
            postDao.toPosts()
        }
    }

    override suspend fun deletePost(post: Post) {
        val localPost = postDao.getPost(post.id.toString())
        postDao.updatePost(localPost.copy(isDeleted = true))
    }

}