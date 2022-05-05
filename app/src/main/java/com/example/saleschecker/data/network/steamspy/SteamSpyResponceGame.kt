package com.example.saleschecker.data.network.steamspy

import com.example.saleschecker.data.local.games.GameEntity
import com.google.gson.annotations.SerializedName

data class SteamSpyResponceGame(
    @SerializedName("appid")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("positive")
    val positive: Int,

    @SerializedName("negative")
    val negative: Int,

    @SerializedName("price")
    val price: String,

    @SerializedName("initialprice")
    val initialPrice: String,

    @SerializedName("discount")
    val discount: String,
) {

    fun convertToGameEntity(): GameEntity {
        return GameEntity(
            id = id,
            name = name,
            review_percent = positive / (positive + negative) * 100,
            price = price.toFloat() / 100, // in USD TODO convert to local currency
            discount_pct = discount.toInt(),
            is_free_game = price.toInt() == 0,
            currency = "USD",
        )
    }

}
