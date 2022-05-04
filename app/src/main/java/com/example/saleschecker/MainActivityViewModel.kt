package com.example.saleschecker

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.di.WorkerCreator
import com.example.saleschecker.mutual.BaseViewModel
import com.example.saleschecker.mutual.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userDao: UserDao,
    private val workerCreator: WorkerCreator
): BaseViewModel() {

    val currentCountryCode: LiveData<String?> = userDao.getCountryCodeLiveData()

    fun setCurrency(countryCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.updateCountryCode(countryCode)

            workerCreator.createPriceUpdater(Constants.UPDATE_CURRENCY)
        }

    }
}