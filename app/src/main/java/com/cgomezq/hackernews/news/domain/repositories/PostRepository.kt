package com.cgomezq.hackernews.news.domain.repositories

import com.cgomezq.hackernews.news.domain.entities.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
    suspend fun deletePost(post: Post)
}