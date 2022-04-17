package com.example.saleschecker.data.network.steam

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SteamApiClient {


    @GET("wishlist/profiles/{user_id}/wishlistdata/")
    suspend fun getWishlist(
        @Path("user_id") profileId: Long,
        @Query("p") page: Int = 0,
        @Query("cc") countryCode: String = "US",
    ): Map<String, SteamResponseGame>?
}