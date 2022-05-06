package com.example.saleschecker.data.network.steam

import com.google.gson.annotations.SerializedName


class SteamPriceUpdate(
    @SerializedName("currency")
    val currency: String,

    @SerializedName("final")
    val price: Int,

    @SerializedName("discount_percent")
    val discountPercent: Int,
) {
    companion object {
        const val PRICE_KEY = "price_overview"
    }
}