package com.cgomezq.hackernews.news.presentation.ui.effects

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.cgomezq.hackernews.R
import com.cgomezq.hackernews.news.presentation.logic.NewsEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun NewsEffects(
    newsEffect: Flow<NewsEffect>,
    snackBarHostState: SnackbarHostState
) {
    val resources = LocalContext.current.resources

    LaunchedEffect(Unit) {
        newsEffect.collect { effect ->
            when (effect) {
                NewsEffect.RefreshingError -> {
                    snackBarHostState.showSnackbar(
                        resources.getString(R.string.error_refreshing_page)
                    )
                }

                NewsEffect.DeletingError -> {
                    snackBarHostState.showSnackbar(
                        resources.getString(R.string.error_deleting_the_post)
                    )
                }

                NewsEffect.LoadingPostError -> {
                    snackBarHostState.showSnackbar(
                        resources.getString(R.string.error_loading_post_page)
                    )
                }
            }
        }
    }
}