package com.example.saleschecker.data.network.steamspy

import android.util.Log
import com.example.saleschecker.data.local.GameEntity
import com.example.saleschecker.data.local.GamesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val TAG = "SteamSpyRepository"

class SteamSpyRepository @Inject constructor(
    private val steamSpyApi: SteamSpyApiClient,
    private val gamesDao: GamesDao,
) {

    suspend fun updateTop100in2Weeks() {
        val response = steamSpyApi.getTop100in2Weeks()
        Log.e(TAG, "updateTop100in2Weeks: ${ response.toString() }", )
        val convertedGames: ArrayList<GameEntity> = arrayListOf()

        if (response != null && response.isNotEmpty()) {
            response.forEach { entry ->
                convertedGames.add(entry.value.convertToGameEntity())
            }
            convertedGames.forEach {
                Log.e(com.example.saleschecker.data.network.steam.TAG, "updateTop100in2Weeks: ${ it.name }", )
            }
            gamesDao.saveGames(convertedGames.toList())
        } else {
            Log.e(com.example.saleschecker.data.network.steam.TAG, "updateTop100in2Weeks: ${ response.toString() }", )
        }


    }
    fun getListOfGames(): Flow<List<GameEntity>> {
        return gamesDao.getListOfAllGames()
    }
}