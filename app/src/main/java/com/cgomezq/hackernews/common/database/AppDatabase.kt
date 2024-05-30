package com.cgomezq.hackernews.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cgomezq.hackernews.news.data.models.PostLocalModel
import com.cgomezq.hackernews.news.data.sources.PostDao

@Database(entities = [PostLocalModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}