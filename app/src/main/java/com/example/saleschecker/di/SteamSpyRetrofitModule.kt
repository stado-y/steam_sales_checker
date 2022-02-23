package com.example.saleschecker.di

import com.example.saleschecker.data.network.steamspy.SteamSpyApiClient
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
class SteamSpyRetrofitModule {


    @Provides
    @Singleton
    fun provideSteamSpyRetrofit(): SteamSpyApiClient {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()

        return Retrofit.Builder()
            .baseUrl("https://steamspy.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SteamSpyApiClient::class.java)
    }

}

