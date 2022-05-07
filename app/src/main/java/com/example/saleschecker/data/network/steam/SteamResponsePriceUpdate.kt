package com.example.saleschecker.data.network.steam

import com.google.gson.annotations.SerializedName

data class SteamResponsePriceUpdate(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: Map<String, SteamPriceUpdate>
)

