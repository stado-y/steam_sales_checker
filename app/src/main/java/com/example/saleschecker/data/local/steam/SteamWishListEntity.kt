package com.example.saleschecker.data.local.steam

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.saleschecker.data.local.steam.SteamWishListEntity.Companion.STEAM_WISHLIST_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = STEAM_WISHLIST_TABLE)
data class SteamWishListEntity(

    @SerializedName("gameId")
    @PrimaryKey
    val gameId: Int,
) {

    companion object {
        const val STEAM_WISHLIST_TABLE = "steam_wishlist_table"
    }
}