package com.example.saleschecker.fragments.webviewfragment

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

interface UserIdReceiver {
    fun receiveSteamUserId (userId: String?)
}


class SteamAuthWebViewClient(
    private val PARAM: String,
    private val receiver: UserIdReceiver,
): WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        val parsedUrl = Uri.parse(url)

        if (parsedUrl.authority == PARAM.lowercase()) {
            view?.stopLoading()
            val userAccountUrl = Uri.parse(parsedUrl.getQueryParameter("openid.identity"))
            val userId = userAccountUrl.lastPathSegment

            receiver.receiveSteamUserId(userId)
            Log.e(TAG, "onPageStarted: Send userId : $userId", )

        }
    }
}