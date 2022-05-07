package com.example.saleschecker.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewStub


fun ViewStub.gone() {
    this.visibility = GONE
}

fun ViewStub.visible() {
    this.visibility = VISIBLE
}

inline fun ViewStub.showSelf(crossinline observer: () -> Unit) {
    parent?.let {
        inflate().setOnClickListener {
            observer()
        }
    } ?: visible()
}

fun ViewStub.hideSelf() {
    parent ?: gone()
}