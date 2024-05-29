package com.cgomezq.hackernews.news.data.repositories

import com.cgomezq.hackernews.common.network.getBodyOrError
import com.cgomezq.hackernews.news.data.mappings.PostMappings
import com.cgomezq.hackernews.news.data.sources.NewsService
import com.cgomezq.hackernews.news.domain.entities.Post
import com.cgomezq.hackernews.news.domain.repositories.PostRepository
import javax.inject.Inject

class ActualPostRepository @Inject constructor(
    private val service: NewsService,
    private val mappings: PostMappings
) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        val response = service.getNews(query = "mobile")
        val body = response.getBodyOrError()
        return with(mappings) {
            body.hits.toPosts()
        }
    }

    override suspend fun deletePost(post: Post) {

    }

}