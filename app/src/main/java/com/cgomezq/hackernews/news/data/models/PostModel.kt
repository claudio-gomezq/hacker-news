package com.cgomezq.hackernews.news.data.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class PostModel(
    val objectID: String?,
    val author: String?,
    @JsonNames("story_title", "title")
    val storyTitle: String?,
    @SerialName("created_at_i")
    val createdAt: Long?,
    @JsonNames("story_url", "url")
    val storyUrl: String?
)