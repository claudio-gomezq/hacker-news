package com.cgomezq.hackernews.news.data.bindings

import com.cgomezq.hackernews.common.database.AppDatabase
import com.cgomezq.hackernews.news.data.sources.NewsService
import com.cgomezq.hackernews.news.data.sources.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {
    @Provides
    fun providesService(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @Provides
    fun providePostDao(db: AppDatabase): PostDao =
        db.postDao()
}