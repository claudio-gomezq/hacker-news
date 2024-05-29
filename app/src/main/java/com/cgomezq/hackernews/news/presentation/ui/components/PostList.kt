package com.cgomezq.hackernews.news.presentation.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import com.cgomezq.hackernews.news.presentation.logic.NewsIntent
import com.cgomezq.hackernews.news.presentation.logic.NewsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostList(
    state: NewsState.ShowingNews,
    emitIntent: (NewsIntent) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = {
            emitIntent(NewsIntent.RefreshNews)
        }
    ) {
        LazyColumn {
            items(state.posts) {
                PostItem(post = it, onClick = {})
                HorizontalDivider()
            }
        }
    }
}