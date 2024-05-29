package com.cgomezq.hackernews.news.domain.usecases

import com.cgomezq.hackernews.news.domain.entities.Post
import com.cgomezq.hackernews.news.domain.repositories.PostRepository
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(): List<Post> = repository.getPosts()
}