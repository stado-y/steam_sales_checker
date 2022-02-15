package com.example.saleschecker.mutual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun <T> execute(
        request: suspend () -> T,
        success: (T?) -> Unit = {},
        error: () -> Unit = {}
    ) {
        viewModelScope.launch(Dispatchers.IO + getErrorHandler(error)) {
            success(request())
        }
    }


    protected open fun showError(error: Throwable?) {}

    protected fun getErrorHandler(errorCallback: (() -> Unit)? = null): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            showError(exception)
            errorCallback?.invoke()
        }
    }
}