package com.cgomezq.hackernews.news.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NewsBody(
    val hits: List<PostModel>
)
