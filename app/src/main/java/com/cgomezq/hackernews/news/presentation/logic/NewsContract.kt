package com.cgomezq.hackernews.news.presentation.logic

import com.cgomezq.hackernews.news.domain.entities.Post

sealed class NewsState {
    data object Loading : NewsState()

    data class ShowingNews(
        val posts: List<Post>
    ) : NewsState()
}

sealed class NewsIntent {
    data object GetPosts : NewsIntent()

    data class DeletePost(val post: Post) : NewsIntent()
}