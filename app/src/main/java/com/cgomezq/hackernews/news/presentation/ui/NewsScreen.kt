package com.cgomezq.hackernews.news.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cgomezq.hackernews.R
import com.cgomezq.hackernews.common.ui.theme.HackerNewsTheme
import com.cgomezq.hackernews.news.presentation.logic.NewsState
import com.cgomezq.hackernews.news.presentation.ui.components.PostList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    state: NewsState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_label)) }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            when (state) {
                NewsState.Loading -> Text(text = "Loading")
                is NewsState.ShowingNews -> PostList(posts = state.posts)
            }
        }
    }
}

@Preview
@Composable
fun NewsScreenPreview(){
    HackerNewsTheme {
        NewsScreen(state = NewsState.Loading)
    }
}