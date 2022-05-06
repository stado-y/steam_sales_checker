package com.example.saleschecker.data.local

import android.util.Log
import androidx.room.TypeConverter
import com.example.saleschecker.data.local.steam.SteamPriceUpdateEntity
import com.example.saleschecker.data.network.steam.SteamPriceUpdate
import com.example.saleschecker.data.network.steam.SteamResponsePriceUpdate

object EntityConverters {

    @TypeConverter
    fun convertSteamResponsePriceUpdateToSteamPriceUpdateEntity(
        response: Map<String, SteamResponsePriceUpdate>
    ): List<SteamPriceUpdateEntity> {
        val container: ArrayList<SteamPriceUpdateEntity> = arrayListOf()
        response.forEach {
            try {
                if (it.value.success) {
                    val currentPrice = it.value.data[SteamPriceUpdate.PRICE_KEY]
                    if (currentPrice != null) {
                        container.add(
                            SteamPriceUpdateEntity(
                                id = it.key.toInt(),
                                currency = currentPrice.currency,
                                price = currentPrice.price / 100f,
                                discount_percent = currentPrice.discountPercent,
                            )
                        )
                    }
                }
            } catch (error: Exception) {
                Log.e(
                    "EntityConverters",
                    "convertSteamResponsePriceUpdateToSteamPriceUpdateEntity: ",
                    error
                )
            }
        }
        return container.toList()
    }
}