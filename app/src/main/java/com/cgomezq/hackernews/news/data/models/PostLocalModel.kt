package com.cgomezq.hackernews.news.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "post")
data class PostLocalModel(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "story_title")
    val storyTitle: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long?,
    @ColumnInfo(name = "story_url")
    val storyUrl: String?,
    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean = false
)