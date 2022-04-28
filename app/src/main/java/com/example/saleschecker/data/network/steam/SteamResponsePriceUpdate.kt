package com.example.saleschecker.data.network.steam

import com.google.gson.annotations.SerializedName
import java.util.*



data class SteamResponsePriceUpdate(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: Map<String, SteamPriceUpdate>

) {
    companion object {
        const val PRICE_KEY = "price_overview"
    }
}

class SteamPriceUpdate (
    @SerializedName("currency")
    val currency: String,

    @SerializedName("final")
    val price: Int,

    @SerializedName("discount_percent")
    val discountPercent: Int,
)
