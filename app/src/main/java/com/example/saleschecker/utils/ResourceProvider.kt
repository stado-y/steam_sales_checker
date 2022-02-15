package com.example.saleschecker.utils

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(
    private val app: Application
) {
    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(app, resId)
    }
}