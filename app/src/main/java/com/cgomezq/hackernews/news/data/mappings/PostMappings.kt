package com.cgomezq.hackernews.news.data.mappings

import com.cgomezq.hackernews.news.data.models.PostModel
import com.cgomezq.hackernews.news.domain.entities.Post
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PostMappings @Inject constructor() {

    fun List<PostModel>.toPosts(): List<Post> = map {
        Post(
            id = it.objectID?.toInt() ?: 0,
            title = it.storyTitle ?: "",
            date = it.createdAt?.toDate() ?: LocalDate.now(),
            authorName = it.author ?: "",
            link = it.storyUrl ?: ""
        )
    }

    private fun String.toDate(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return LocalDate.parse(this, formatter)
    }
}