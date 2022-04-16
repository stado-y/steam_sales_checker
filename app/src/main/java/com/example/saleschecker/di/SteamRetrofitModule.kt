package com.example.saleschecker.di

import com.example.saleschecker.data.network.steam.SteamApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SteamRetrofitModule {

    @Provides
    @Singleton
    fun provideSteamRetrofit(
        @OkHttpClientWithLoggingInterceptor okHttpClient: OkHttpClient
    ): SteamApiClient {


        val retrofit = Retrofit.Builder()
            .baseUrl("https://store.steampowered.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SteamApiClient::class.java)
    }
}