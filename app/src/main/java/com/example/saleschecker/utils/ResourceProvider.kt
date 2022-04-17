package com.example.saleschecker.utils

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "ResourceProvider"

@Singleton
class ResourceProvider @Inject constructor(
    private val app: Application
) {

    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(app, resId)
    }

    fun getLocale(): Locale {
        return ConfigurationCompat.getLocales(app.resources.configuration)[0]
    }

    fun getCurrency(countryCode: String = getLocale().country): String {
        Log.d(TAG, "getCurrency: locale: ${Locale(countryCode)}")
        return Currency.getInstance(Locale(getLocale().language, countryCode)).currencyCode
    }
}