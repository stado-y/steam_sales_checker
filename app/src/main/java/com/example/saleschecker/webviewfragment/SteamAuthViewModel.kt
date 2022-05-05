package com.example.saleschecker.webviewfragment

import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.mutual.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SteamAuthViewModel @Inject constructor(
    private val userDao: UserDao
): BaseViewModel() {

    fun saveUserId(userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.updateUserId(userId)
        }

    }
}