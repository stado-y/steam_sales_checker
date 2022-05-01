package com.example.saleschecker.utils

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import com.example.saleschecker.R
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "ResourceProvider"
const val DEFAULT_EUROPE_COUNTRY_CODE = "FR"
const val EURO_CODE = "EUR"

@Singleton
class ResourceProvider @Inject constructor(
    private val app: Application
) {

    private val countriesWithEuro = app.resources.getStringArray(R.array.countries_with_euro)

    fun getLocale(): Locale {
        return ConfigurationCompat.getLocales(app.resources.configuration)[0]
    }

    fun getCurrency(countryCode: String = getLocale().country): String {
        Log.d(TAG, "getCurrency: locale: ${Locale(countryCode)}")
        return Currency.getInstance(Locale(getLocale().language, countryCode)).currencyCode
    }

    fun getCountryCodeFromCurrency(currency: String): String {
        if (currency.length != 3) {
            throw IllegalArgumentException(
                "getCountryCodeFromCurrency : invalid currency : $currency"
            )
        }
        return if (currency.uppercase() == EURO_CODE) {
            var currentCountryCode = getLocale().country
            if (currentCountryCode !in countriesWithEuro) {
                currentCountryCode = DEFAULT_EUROPE_COUNTRY_CODE
            }
            currentCountryCode
        } else {
            currency.dropLast(1).uppercase()
        }
    }

    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(app, resId)
    }

    fun getCurrencyDrawableId(currency: String): Int {
        val resId =  app.resources.getIdentifier(
            currency.dropLast(1).lowercase(),
            "drawable", app.packageName,
        )
        Log.e(TAG, "getCurrencyDrawableId: Resid : $resId", )
        return resId
    }

    fun getCurrencyDrawable(currency: String): Drawable? {
        return getDrawable(getCurrencyDrawableId(currency))
    }
}