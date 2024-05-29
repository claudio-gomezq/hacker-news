package com.cgomezq.hackernews.news.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cgomezq.hackernews.R
import com.cgomezq.hackernews.common.formatter.toTimesAgo
import com.cgomezq.hackernews.common.ui.theme.HackerNewsTheme
import com.cgomezq.hackernews.news.domain.entities.Post
import java.time.LocalDateTime

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post,
    onClick: (Post) -> Unit
) {
    val timeAgo = post.date.toTimesAgo(stringResource(id = R.string.yesterday))
    Column(
        modifier = modifier
            .clickable { onClick(post) }
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Text(text = post.title)
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.author_date, post.authorName, timeAgo),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Preview
@Composable
fun PostItemPreview() {
    HackerNewsTheme {
        PostItem(
            modifier = Modifier.fillMaxWidth(),
            post = Post(
                id = 0,
                title = "Hackers news",
                date = LocalDateTime.now().minusHours(6),
                authorName = "Arturito",
                link = "www.google.com"
            ),
            onClick = {}
        )
    }
}