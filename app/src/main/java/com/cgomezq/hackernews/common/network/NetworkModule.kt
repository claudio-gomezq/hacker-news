package com.cgomezq.hackernews.common.network

import android.content.Context
import com.cgomezq.hackernews.common.network.qualifiers.BaseUrl
import com.cgomezq.hackernews.common.network.qualifiers.NetworkAvailable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @BaseUrl
    fun providesBaseUrl(): String =
        "https://hn.algolia.com/api/v1/"

    @Provides
    @NetworkAvailable
    fun providesIsNetWorkAvailable(@ApplicationContext context: Context): Boolean =
        context.isNetworkAvailable()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

    @Provides
    fun provideConverterFactory(json: Json): Converter.Factory =
        json.asConverterFactory(MediaType.get("application/json; charset=UTF8"))

    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .build()

}