package com.example.saleschecker.data.network.steam

import android.util.Log
import com.example.saleschecker.data.local.GameEntity
import com.example.saleschecker.data.local.GamesDao
import com.example.saleschecker.data.local.UserDao
import com.example.saleschecker.data.local.UserEntity
import com.example.saleschecker.utils.ResourceProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val TAG = "SteamRepository"

class SteamRepository @Inject constructor (
    private val steamApi: SteamApiClient,
    private val userDao: UserDao,
    private val gamesDao: GamesDao,
    private val resourceProvider: ResourceProvider,
) {
    suspend fun updateWishList() {
        val response = steamApi.getWishlist(userDao.getUserId())
        Log.e(TAG, "updateWishList: wishlist response : ${response.toString()}", )
        val convertedGames: ArrayList<GameEntity> = arrayListOf()

        if (response != null && response.isNotEmpty()) {
            response.forEach { entry ->
                convertedGames.add(
                    entry.value.convertToGameEntity(
                        entry.key.toInt(),
                        resourceProvider.getCurrency()
                    )
                )
            }
            convertedGames.forEach {
                Log.e(TAG, "updateWishList: ${ it.name }", )
            }
            gamesDao.saveGames(convertedGames.toList())
        } else {
            Log.e(TAG, "updateWishList: ${ response.toString() }", )
        }

    }
    suspend fun saveUser(
        id: Long = userDao.getUserId(),
        currency: String = getCurrency(),
    ) {
        userDao.saveUser(UserEntity(id, currency))
    }

    private fun getCurrency(): String {
        return userDao.getCurrency() ?: resourceProvider.getCurrency()
    }

    fun getListOfGames(): Flow<List<GameEntity>> {
        return gamesDao.getListOfAllGames()
    }
}