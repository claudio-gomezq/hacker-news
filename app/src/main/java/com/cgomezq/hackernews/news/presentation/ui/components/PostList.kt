package com.cgomezq.hackernews.news.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cgomezq.hackernews.news.domain.entities.Post
import com.cgomezq.hackernews.news.presentation.logic.NewsIntent
import com.cgomezq.hackernews.news.presentation.logic.NewsState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostList(
    state: NewsState.ShowingNews,
    emitIntent: (NewsIntent) -> Unit,
    onClickPost: (Post) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { emitIntent(NewsIntent.RefreshNews) }
    ) {
        LazyColumn {
            items(items = state.posts, key = { it.id }) {
                SwipeToDismissItem(
                    modifier = Modifier.animateItemPlacement(),
                    onDismissed = { emitIntent(NewsIntent.DeletePost(it)) },
                    content = {
                        Column {
                            PostItem(
                                post = it,
                                onClick = onClickPost
                            )
                            HorizontalDivider()
                        }
                    }
                )
            }
        }
    }

}