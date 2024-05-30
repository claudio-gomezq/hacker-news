package com.cgomezq.hackernews.news.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cgomezq.hackernews.R
import com.cgomezq.hackernews.common.ui.theme.HackerNewsTheme
import com.cgomezq.hackernews.news.presentation.logic.NewsIntent
import com.cgomezq.hackernews.news.presentation.logic.NewsState
import com.cgomezq.hackernews.news.presentation.ui.components.ErrorState
import com.cgomezq.hackernews.news.presentation.ui.components.PostList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    state: NewsState,
    snackBarHostState: SnackbarHostState,
    emitIntent: (NewsIntent) -> Unit,
    navigateToPost: (title: String, link: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_label)) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (state) {
                NewsState.Loading ->
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )

                is NewsState.ShowingNews ->
                    PostList(
                        state = state,
                        emitIntent = emitIntent,
                        onClickPost = { post ->
                            navigateToPost(post.title, post.link)
                        }
                    )

                NewsState.ShowingError ->
                    ErrorState(
                        onTryAgain = {
                            emitIntent(NewsIntent.GetNews)
                        }
                    )
            }
        }
    }
}

@Preview
@Composable
fun NewsScreenPreview() {
    HackerNewsTheme {
        NewsScreen(
            state = NewsState.Loading,
            snackBarHostState = remember { SnackbarHostState() },
            emitIntent = {},
            navigateToPost = { _, _ -> }
        )
    }
}