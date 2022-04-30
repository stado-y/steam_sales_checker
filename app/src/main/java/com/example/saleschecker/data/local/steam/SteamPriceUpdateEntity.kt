package com.example.saleschecker.data.local.steam

import androidx.room.ColumnInfo
import androidx.room.Update
import com.example.saleschecker.data.local.games.GameEntity


data class SteamPriceUpdateEntity (

    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "currency")
    val currency: String = "Default",

    @ColumnInfo(name = "price")
    val price: Float,

    @ColumnInfo(name =  "discount_pct")
    val discount_percent: Int,
)
