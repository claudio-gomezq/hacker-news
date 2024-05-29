package com.cgomezq.hackernews.news.data.sources

import com.cgomezq.hackernews.news.data.models.NewsBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("search_by_date")
    suspend fun getNews(@Query("query") query: String): Response<NewsBody>
}