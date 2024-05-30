package com.cgomezq.hackernews.news.data.mappings

import com.cgomezq.hackernews.news.data.models.PostLocalModel
import com.cgomezq.hackernews.news.data.models.PostModel
import com.cgomezq.hackernews.news.domain.entities.Post
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class PostMappings @Inject constructor() {

    fun List<PostLocalModel>.toPosts(): List<Post> = map {
        Post(
            id = it.id.toInt(),
            title = it.storyTitle ?: "",
            date = it.createdAt?.toLocalDateTime() ?: LocalDateTime.now(),
            authorName = it.author ?: "",
            link = it.storyUrl ?: ""
        )
    }

    fun List<PostModel>.toPostsLocalModel(): List<PostLocalModel> = map {
        PostLocalModel(
            id = it.objectID ?: "",
            storyTitle = it.storyTitle,
            createdAt = it.createdAt,
            author = it.author,
            storyUrl = it.storyUrl,
        )
    }

    private fun Long.toLocalDateTime(): LocalDateTime {
        return Instant.ofEpochSecond(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}