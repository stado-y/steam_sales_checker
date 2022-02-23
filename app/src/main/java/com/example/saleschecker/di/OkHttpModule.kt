package com.example.saleschecker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpClientWithLoggingInterceptor


@InstallIn(SingletonComponent::class)
@Module
object OkHttpModule {

    @Provides
    @OkHttpClientWithLoggingInterceptor
    @Singleton
    fun provideOkHttpClientWithLoggingInterceptor(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()
    }
}