package com.cgomezq.hackernews.news.presentation.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import com.cgomezq.hackernews.news.domain.entities.Post

@Composable
fun PostList(posts: List<Post>) {
    LazyColumn {
        items(posts) {
            PostItem(post = it, onClick = {})
            HorizontalDivider()
        }
    }
}