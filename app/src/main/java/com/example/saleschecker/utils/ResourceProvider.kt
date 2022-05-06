package com.example.saleschecker.utils

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import com.example.saleschecker.R
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "ResourceProvider"
const val DEFAULT_EUROPE_COUNTRY_CODE = "FR"
const val EURO_CODE = "EUR"
const val EURO_FLAG = "EU"
const val DEFAULT_COUNTRY = "US"
const val DEFAULT_CURRENCY = "USD"
const val DRAWABLE_TYPE = "drawable"

@Singleton
class ResourceProvider @Inject constructor(
    private val app: Application
) {

    private val countriesWithEuro = app.resources.getStringArray(R.array.countries_with_euro)
    private val allCurrencies = app.resources.getStringArray(R.array.currencies_all)
    private val countriesWithOwnCurrency: List<String> = allCurrencies.mapNotNull {
        if (it != EURO_CODE || it.length == 3) {
            it.substring(0, 2)
        } else {
            null
        }
    }

    fun getAllCurrencies(): Array<String> {
        return allCurrencies
    }

    fun getLocale(): Locale {
        return ConfigurationCompat.getLocales(app.resources.configuration)[0]
    }

    fun getCurrency(countryCode: String = getLocale().country): String {
        Log.d(TAG, "getCurrency: locale: ${Locale(countryCode)}")
        return Currency.getInstance(Locale(getLocale().language, countryCode)).currencyCode
    }

    fun getCountryCodeFromCurrency(currency: String): String {
        if (currency !in allCurrencies) {
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

    private fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(app, resId)
    }

    private fun getDrawableId(drawableName: String): Int {
        return app.resources.getIdentifier(
            drawableName.lowercase(),
            DRAWABLE_TYPE,
            app.packageName,
        )
    }

    fun getCurrencyDrawable(currency: String): Drawable? {
        if (currency.length != 3) {
            throw IllegalArgumentException(
                "getCurrencyDrawable : invalid currency : $currency"
            )
        }
        val flagName = when (currency) {
            in allCurrencies -> currency.substring(0, 2)
            else -> DEFAULT_COUNTRY
        }
        return getDrawable(getDrawableId(flagName))
    }

    fun getCountryCodeDrawable(countryCode: String?): Drawable? {
        val flagName = when (countryCode) {
            in countriesWithEuro -> EURO_FLAG
            in countriesWithOwnCurrency -> countryCode as String
            else -> DEFAULT_COUNTRY
        }
        return getDrawable(getDrawableId(flagName))
    }
}