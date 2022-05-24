package com.example.saleschecker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.data.local.user.UserEntity
import com.example.saleschecker.di.WorkerCreator
import com.example.saleschecker.mutual.BaseViewModel
import com.example.saleschecker.mutual.Constants
import com.example.saleschecker.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userDao: UserDao,
    private val workerCreator: WorkerCreator,
    private val resourceProvider: ResourceProvider,
): BaseViewModel() {

    val currentCountryCode: LiveData<String?> = userDao.getCountryCodeLiveData()

    init {
        Log.e(TAG, "BuildConfig.BUILD_TYPE : ${ BuildConfig.BUILD_TYPE } ", )
        if (BuildConfig.BUILD_TYPE == Constants.DEBUG_BUILD_TYPE) {
            execute(request = {
                if (userDao.getUserId() != Constants.DEBUG_USER_ID) {
                    userDao.saveUser(
                        UserEntity(
                            id = Constants.DEBUG_USER_ID,
                            countryCode = resourceProvider.getLocale().country
                        )
                    )
                }
            })
        } else {
            execute( request = ::saveDefaultUser )
        }
    }

    private suspend fun saveDefaultUser() {
        userDao.getSavedUserId() ?: run {
            userDao.saveUser(
                UserEntity(
                    id = Constants.DEFAULT_USER_ID,
                    countryCode = resourceProvider.getLocale().country
                )
            )
            Log.e(TAG, "saveDefaultUser: Saving default user", )
        }
    }

    fun setCurrency(countryCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.updateCountryCode(countryCode)

            workerCreator.createPriceUpdater(Constants.UPDATE_CURRENCY)
        }

    }
}