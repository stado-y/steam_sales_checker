package com.example.saleschecker.data.network.steam

import com.google.gson.annotations.SerializedName

data class SteamResponseGameDetails(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: SteamGameDetails,
)

data class SteamGameDetails(
    @SerializedName("type")
    val type: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("is_free")
    val is_free: Boolean,

    @SerializedName("controller_support")
    val controllerSupport: String,

    @SerializedName("detailed_description")
    val DetailedDescription: String,

    @SerializedName("short_description")
    val shortDescription: String,

    @SerializedName("header_image")
    val header: String,

    @SerializedName("background")
    val background: String,

    @SerializedName("background_raw")
    val backgroundRaw: String,

    @SerializedName(SteamPriceUpdate.PRICE_KEY)
    val price: SteamPriceUpdate,

    @SerializedName("screenshots")
    val screenshots: List<SteamScreenShot>,

    )

data class SteamScreenShot(
    @SerializedName("id")
    val id: Int,

    @SerializedName("path_thumbnail")
    val thumbnail: String,

    @SerializedName("path_full")
    val full: String,
)