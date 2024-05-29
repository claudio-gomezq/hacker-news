package com.cgomezq.hackernews.news.domain.entities

import java.time.LocalDate

data class Post(
    val id: Int,
    val title: String,
    val date: LocalDate,
    val authorName: String,
    val link: String
)
