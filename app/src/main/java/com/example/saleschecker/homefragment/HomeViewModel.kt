package com.example.saleschecker.homefragment

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.saleschecker.BuildConfig
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.data.local.user.UserEntity
import com.example.saleschecker.data.network.steamspy.SteamSpyRepository
import com.example.saleschecker.di.WorkerCreator
import com.example.saleschecker.mutual.BaseViewModel
import com.example.saleschecker.mutual.Constants
import com.example.saleschecker.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserDao,
    private val repository: SteamSpyRepository,
    private val workerCreator: WorkerCreator,
    private val resourceProvider: ResourceProvider,
) : BaseViewModel() {


    val games: Flow<List<GameEntity>> = repository.getTopList()

    val userId: LiveData<Long?> = userDao.getUserIdLiveData()

    init {
        execute( request = { repository.updateTop100in2Weeks() }, success = ::afterUpdate)

        if (BuildConfig.BUILD_TYPE == Constants.DEBUG_BUILD_TYPE) {
            runBlocking {
                userDao.saveUser(
                    UserEntity(
                        id = Constants.DEBUG_USER_ID,
                        countryCode = resourceProvider.getLocale().country
                    )
                )
            }
        }
    }
    private fun <T> afterUpdate(result: T?) {
        Log.e("HomeViewModel", "afterUpdate: CALL", )
        workerCreator.createPriceUpdater(Constants.UPDATE_STEAMSPY_PRICES)
    }
}