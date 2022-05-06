package com.example.saleschecker.utils

import com.example.saleschecker.BuildConfig
import com.example.saleschecker.mutual.Constants

object UrlBuilder {

    private var imageCurrentUrlNumber = 0
        @Synchronized get
        @Synchronized set

    fun getImageUrl(
        gameId: Int,
        size: String = Constants.IMAGE_CAPSULE
    ): String {
        return when (imageCurrentUrlNumber) {
            0 -> {
                imageCurrentUrlNumber = 1
                "${BuildConfig.IMAGE_BASE_URL}$gameId/$size"
            }
            else -> {
                imageCurrentUrlNumber = 0
                "${BuildConfig.IMAGE_SECONDARY_URL}$gameId/$size"
            }
        }
    }
}