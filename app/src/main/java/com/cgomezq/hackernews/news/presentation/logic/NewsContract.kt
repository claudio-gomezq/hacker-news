package com.cgomezq.hackernews.news.presentation.logic

import com.cgomezq.hackernews.news.domain.entities.Post

sealed class NewsState {
    data object Loading : NewsState()

    data class ShowingNews(
        val isRefreshing: Boolean,
        val posts: List<Post>
    ) : NewsState()
}

sealed class NewsIntent {
    data object GetNews : NewsIntent()

    data object RefreshNews : NewsIntent()

    data class DeletePost(val post: Post) : NewsIntent()
}