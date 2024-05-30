package com.cgomezq.hackernews.news.presentation.logic

import com.cgomezq.hackernews.news.domain.entities.Post

sealed class NewsState {

    data object Loading : NewsState()

    data class ShowingNews(
        val isRefreshing: Boolean,
        val posts: List<Post>
    ) : NewsState()

    data object ShowingError : NewsState()
}

sealed class NewsIntent {

    data object GetNews : NewsIntent()

    data object RefreshNews : NewsIntent()

    data class DeletePost(val post: Post) : NewsIntent()
}

sealed class NewsEffect {

    data object RefreshingError : NewsEffect()

    data object DeletingError : NewsEffect()

    data object LoadingPostError : NewsEffect()
}