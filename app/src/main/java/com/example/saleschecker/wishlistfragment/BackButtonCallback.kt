package com.example.saleschecker.wishlistfragment

import android.util.Log
import androidx.activity.OnBackPressedCallback

interface BackButtonCallbackReceiver {
    fun onBackButtonPressed()
}

class BackButtonCallback(
    private val receiver: BackButtonCallbackReceiver,
    enabled: Boolean = true,
) : OnBackPressedCallback(enabled) {

    override fun handleOnBackPressed() {
        receiver.onBackButtonPressed()
        Log.e(TAG, "handleOnBackPressed: ", )
    }
}