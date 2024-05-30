package com.cgomezq.hackernews.news.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    LaunchedEffect(pullToRefreshState) {
        snapshotFlow { pullToRefreshState.isRefreshing }
            .collect {
                if (it) {
                    onRefresh()
                }
            }

    }
    if (!isRefreshing) {
        pullToRefreshState.endRefresh()
    }
    Box(Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        content()
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState
        )
    }
}