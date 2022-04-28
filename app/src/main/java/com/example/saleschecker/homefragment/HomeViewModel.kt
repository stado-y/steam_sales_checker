package com.example.saleschecker.homefragment

import android.util.Log
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.data.network.steamspy.SteamSpyRepository
import com.example.saleschecker.di.WorkerCreator
import com.example.saleschecker.mutual.BaseViewModel
import com.example.saleschecker.mutual.Constants
import com.example.saleschecker.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SteamSpyRepository,
    private val workerCreator: WorkerCreator,
    private val resourceProvider: ResourceProvider,
) : BaseViewModel() {


    val games: Flow<List<GameEntity>> = repository.getTopList()

    init {
        execute( request = { repository.updateTop100in2Weeks() }, success = ::afterUpdate)
    }
    private fun <T> afterUpdate(result: T?) {
        Log.e("HomeViewModel", "afterUpdate: CALL", )
        workerCreator.createPriceUpdater(Constants.UPDATE_STEAMSPY_PRICES)
    }
}