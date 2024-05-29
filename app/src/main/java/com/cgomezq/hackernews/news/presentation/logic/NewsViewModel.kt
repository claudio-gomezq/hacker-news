package com.cgomezq.hackernews.news.presentation.logic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgomezq.hackernews.common.ui.extensions.transformFold
import com.cgomezq.hackernews.news.domain.usecases.GetPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getPosts: GetPosts
) : ViewModel() {

    private val intents = MutableSharedFlow<NewsIntent>()

    val state: StateFlow<NewsState> = intents
        .onSubscription { emit(NewsIntent.GetNews) }
        .transformFold<NewsIntent, NewsState>(
            initial = NewsState.Loading,
        ) { previousState, intent, emit ->
            when (intent) {
                NewsIntent.GetNews -> runCatching {
                    emit(NewsState.Loading)
                    val posts = getPosts()
                    emit(NewsState.ShowingNews(isRefreshing = false, posts = posts))
                }.onFailure {
                    Log.e("error", it.localizedMessage ?: "")
                    // TODO handle error
                }

                NewsIntent.RefreshNews -> if (previousState is NewsState.ShowingNews) runCatching {
                    emit(previousState.copy(isRefreshing = true))
                    val posts = getPosts()
                    emit(previousState.copy(posts = posts, isRefreshing = false))
                }.onFailure {
                }

                is NewsIntent.DeletePost -> {
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = NewsState.Loading
        )


    fun emitIntent(intent: NewsIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }
}