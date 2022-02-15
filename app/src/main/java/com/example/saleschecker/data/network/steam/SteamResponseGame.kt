package com.example.saleschecker.data.network.steam

import com.example.saleschecker.data.local.GameEntity
import com.google.gson.annotations.SerializedName

data class SteamResponseGame(

    @SerializedName("name")
    val name: String,

    // URI for game pic
    @SerializedName("capsule")
    val image: String,

    // positive steam review percent
    @SerializedName("reviews_percent")
    val review_percent: Int,

    /*
        price for game and bundle price
        TODO find out how to differ this prices
     */
    @SerializedName("subs")
    val prices: List<SteamGamePrice>,

    // URI for background img
    @SerializedName("background")
    val background: String,

    // game tags on local lang
    @SerializedName("tags")
    val tags: List<String>,

    // lol free games can be wishlisted ?! WHYYYYYYY
    @SerializedName("is_free_game")
    val is_free_game: Boolean,

    // supported platforms (nullable)
    @SerializedName("win")
    val win: Int?,
    @SerializedName("mac")
    val mac: Int?,
    @SerializedName("linux")
    val linux: Int?,
) {
    fun convertToGameEntity(gameId: Int): GameEntity {
        var lowestPrice = Int.MAX_VALUE
        var discount = 0
        for (gamePrice in prices) {
            if (gamePrice.price < lowestPrice) {
                lowestPrice = gamePrice.price
                discount = gamePrice.discount_pct
            }
        }
        return GameEntity(
            id = gameId,
            name = name,
            image = image,
            background = background,
            review_percent = review_percent,
            price = lowestPrice,
            discount_pct = discount,
            is_free_game = is_free_game,
            win = win,
            mac = mac,
            linux = linux,
        )
    }
}

data class SteamGamePrice(
    @SerializedName("id")
    val id: Int,

    // discount percent
    @SerializedName("discount_pct")
    val discount_pct: Int,

    // final price in local currency (в копейках)
    @SerializedName("price")
    val price: Int,
)
