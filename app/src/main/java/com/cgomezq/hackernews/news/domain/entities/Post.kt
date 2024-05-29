package com.cgomezq.hackernews.news.domain.entities

import java.time.LocalDateTime

data class Post(
    val id: Int,
    val title: String,
    val date: LocalDateTime,
    val authorName: String,
    val link: String
)
