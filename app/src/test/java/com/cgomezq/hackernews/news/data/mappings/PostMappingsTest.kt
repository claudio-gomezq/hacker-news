package com.cgomezq.hackernews.news.data.mappings

import com.cgomezq.hackernews.news.data.models.PostLocalModel
import com.cgomezq.hackernews.news.data.models.PostModel
import com.cgomezq.hackernews.news.domain.entities.Post
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMappingsTest {

    @Test
    fun toPosts_givenPostLocalModelList_shouldMapToPost() {
        val mappings = PostMappings()
        val fakePostLocalModelList = listOf<PostLocalModel>()
        val result = with(mappings) {
            fakePostLocalModelList.toPosts()
        }
        assertEquals(listOf<Post>(), result)
    }
    @Test
    fun toPostsLocalModel_givenPostModel_shouldMapToPost() {
        val mappings = PostMappings()
        val fakePostLocalModelList = listOf<PostModel>()
        val result = with(mappings) {
            fakePostLocalModelList.toPostsLocalModel()
        }
        assertEquals(listOf<PostLocalModel>(), result)
    }
}