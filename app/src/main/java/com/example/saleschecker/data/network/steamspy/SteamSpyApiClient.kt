package com.example.saleschecker.data.network.steamspy

import retrofit2.http.GET
import retrofit2.http.Query

interface SteamSpyApiClient {


    @GET("api.php?")
    suspend fun getTop100in2Weeks(
        @Query("request") request: String = "top100in2weeks"
    ): Map<String, SteamSpyResponceGame>
}