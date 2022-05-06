package com.example.saleschecker.data.network.steamspy

import android.util.Log
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.data.local.games.GamesDao
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListDao
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val TAG = "SteamSpyRepository"

class SteamSpyRepository @Inject constructor(
    private val steamSpyApi: SteamSpyApiClient,
    private val gamesDao: GamesDao,
    private val steamSpyTopListDao: SteamSpyTopListDao,
) {

    suspend fun updateTop100in2Weeks() {
        val response = steamSpyApi.getTop100in2Weeks()
        Log.e(TAG, "updateTop100in2Weeks: $response")
        val convertedGames: ArrayList<GameEntity> = arrayListOf()
        val topList: ArrayList<SteamSpyTopListEntity> = arrayListOf()

        if (response != null && response.isNotEmpty()) {
            response.forEach { entry ->
                convertedGames.add(entry.value.convertToGameEntity())
                topList.add(SteamSpyTopListEntity(entry.value.id))
            }
            convertedGames.forEach {
                Log.e(
                    com.example.saleschecker.data.network.steam.TAG,
                    "updateTop100in2Weeks: ${it.name}",
                )
            }
            saveTopList(topList)
            gamesDao.saveSteamSpyGames(convertedGames.toList())
        } else {
            Log.e(
                com.example.saleschecker.data.network.steam.TAG,
                "updateTop100in2Weeks: $response",
            )
        }

    }

    private suspend fun saveTopList(list: List<SteamSpyTopListEntity>) {
        steamSpyTopListDao.deleteTopList()
        steamSpyTopListDao.saveTopList(list)
    }

    fun getListOfGames(): Flow<List<GameEntity>> {
        return gamesDao.getFlowOfAllGames()
    }

    fun getTopList(): Flow<List<GameEntity>> {
        return gamesDao.getSteamSpyTopListFlow()
    }
}