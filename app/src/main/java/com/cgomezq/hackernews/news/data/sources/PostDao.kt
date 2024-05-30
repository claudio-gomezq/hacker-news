package com.cgomezq.hackernews.news.data.sources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cgomezq.hackernews.news.data.models.PostLocalModel

@Dao
interface PostDao {
    @Query("select * from post where is_deleted=0")
    suspend fun getAll(): List<PostLocalModel>

    @Query("select * from post where id=:id")
    suspend fun getPost(id: String): PostLocalModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg posts: PostLocalModel)

    @Update
    suspend fun updatePost(post: PostLocalModel)
}