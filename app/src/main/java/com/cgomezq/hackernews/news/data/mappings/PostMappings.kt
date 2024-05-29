package com.cgomezq.hackernews.news.data.mappings

import com.cgomezq.hackernews.news.data.models.PostModel
import com.cgomezq.hackernews.news.domain.entities.Post
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class PostMappings @Inject constructor() {

    fun List<PostModel>.toPosts(): List<Post> = map {
        Post(
            id = it.objectID?.toInt() ?: 0,
            title = it.storyTitle ?: "",
            date = it.createdAt?.toLocalDateTime() ?: LocalDateTime.now(),
            authorName = it.author ?: "",
            link = it.storyUrl ?: ""
        )
    }

    private fun Long.toLocalDateTime(): LocalDateTime {
        return Instant.ofEpochSecond(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}