package com.cgomezq.hackernews.news.data.bindings

import com.cgomezq.hackernews.news.data.repositories.ActualPostRepository
import com.cgomezq.hackernews.news.domain.repositories.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {
    @Binds
    fun provideRepository(repository: ActualPostRepository): PostRepository
}