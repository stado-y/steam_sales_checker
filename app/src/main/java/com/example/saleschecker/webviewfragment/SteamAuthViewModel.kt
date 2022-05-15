package com.example.saleschecker.webviewfragment

import android.util.Log
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.data.local.user.UserEntity
import com.example.saleschecker.mutual.BaseViewModel
import com.example.saleschecker.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

const val TAG = "SteamAuthViewModel"

@HiltViewModel
class SteamAuthViewModel @Inject constructor(
    private val userDao: UserDao,
    private val resourceProvider: ResourceProvider,
): BaseViewModel() {

    fun saveUser(userId: Long) {
        runBlocking(Dispatchers.IO) {
            Log.e(TAG, "saveUser: before userId : $userId", )
            userDao.saveUser(
                UserEntity(
                    id = userId,
                    countryCode = userDao.getCountryCode()
                        ?: resourceProvider.getLocale().country,
                )
            )
            Log.e(TAG, "saveUser: after userId : $userId", )
        }

    }
}