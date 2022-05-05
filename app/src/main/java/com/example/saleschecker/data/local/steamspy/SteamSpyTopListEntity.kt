package com.example.saleschecker.data.local.steamspy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListEntity.Companion.STEAM_SPY_TOP_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = STEAM_SPY_TOP_TABLE)
data class SteamSpyTopListEntity(
    @SerializedName("gameId")
    @PrimaryKey
    val gameId: Int,
) {
    companion object {
        const val STEAM_SPY_TOP_TABLE = "steam_spy_top_table"
    }
}