package com.cgomezq.hackernews.news.presentation.logic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgomezq.hackernews.news.domain.usecases.GetPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getPosts: GetPosts
) : ViewModel() {

    private val intents = MutableSharedFlow<NewsIntent>()

    val state: StateFlow<NewsState> = intents
        .onSubscription { emit(NewsIntent.GetPosts) }
        .transform { intent ->
            emit(NewsState.Loading)
            when (intent) {
                NewsIntent.GetPosts -> runCatching {
                    val posts = getPosts()
                    emit(NewsState.ShowingNews(posts))
                }.onFailure {
                    Log.e("error", it.localizedMessage ?: "")
                    // TODO handle error
                }
                is NewsIntent.DeletePost -> {
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = NewsState.Loading
        )
}