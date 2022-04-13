package com.example.saleschecker.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import com.example.saleschecker.R
import com.example.saleschecker.mutual.Constants
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(
    private val app: Application
) {
    private val sharedPrefs = app.getSharedPreferences(
        app.getString(R.string.app_preferences_file_name),
        Context.MODE_PRIVATE
    )

    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(app, resId)
    }


    private fun getLocale(): Locale {
        return ConfigurationCompat.getLocales(app.resources.configuration)[0]
    }

    fun getCurrency(): String {
        return sharedPrefs?.getString(Constants.CURRENCY_CODE, null) ?:
            Currency.getInstance(getLocale()).currencyCode
    }
//    fun getUserId(): Long {
//        return sharedPrefs?.getLong(Constants.USER_ID, Constants.DEFAULT_USER_ID) ?:
//            Constants.DEFAULT_USER_ID
//    }

}