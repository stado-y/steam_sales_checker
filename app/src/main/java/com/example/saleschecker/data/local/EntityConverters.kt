package com.example.saleschecker.data.local

import androidx.room.TypeConverter
import com.example.saleschecker.data.local.steam.SteamPriceUpdateEntity
import com.example.saleschecker.data.network.steam.SteamResponsePriceUpdate

object EntityConverters {

    @TypeConverter
    fun convertSteamResponsePriceUpdateToSteamPriceUpdateEntity(
        response: Map<String, SteamResponsePriceUpdate>
    ): List<SteamPriceUpdateEntity> {
        val container: ArrayList<SteamPriceUpdateEntity> = arrayListOf()
        response.forEach() {
            val price = it.value.data[SteamResponsePriceUpdate.PRICE_KEY]
            if (price != null) {
                container.add(
                    SteamPriceUpdateEntity(
                        id = it.key.toInt(),
                        currency = price.currency,
                        final = price.price / 100f,
                        discount_percent = price.discountPercent,
                    )
                )
            }

        }
        return container.toList()
    }
}