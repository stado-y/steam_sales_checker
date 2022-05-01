package com.example.saleschecker.data.network.steam

import android.util.Log
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.data.local.games.GamesDao
import com.example.saleschecker.data.local.steam.SteamWishListDao
import com.example.saleschecker.data.local.steam.SteamWishListEntity
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.data.local.user.UserEntity
import com.example.saleschecker.utils.ResourceProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val TAG = "SteamRepository"

class SteamRepository @Inject constructor (
    private val steamApi: SteamApiClient,
    private val userDao: UserDao,
    private val gamesDao: GamesDao,
    private val steamWishListDao: SteamWishListDao,
    private val resourceProvider: ResourceProvider,
) {
    suspend fun updateWishList() {
        val response = steamApi.getWishlist(
            userDao.getUserId(),
            countryCode = getCountryCode(),
        )
        Log.e(TAG, "updateWishList: wishlist response : ${response.toString()}", )
        val convertedGames: ArrayList<GameEntity> = arrayListOf()
        val wishList: ArrayList<SteamWishListEntity> = arrayListOf()

        if (response != null && response.isNotEmpty()) {
            response.forEach { entry ->
                val gameId = entry.key.toInt()
                convertedGames.add(
                    entry.value.convertToGameEntity(
                        gameId,
                        resourceProvider.getCurrency(getCountryCode()),
                    )
                )
                wishList.add(SteamWishListEntity(gameId))
            }
            convertedGames.forEach {
                Log.e(TAG, "updateWishList: ${ it.name }", )
            }
            saveWishList(wishList.toList())
            gamesDao.saveSteamGames(convertedGames.toList())
        } else {
            Log.e(TAG, "updateWishList: ${ response.toString() }", )
        }

    }
    suspend fun saveUser(
        id: Long = userDao.getUserId(),
        currency: String = getCountryCode(),
    ) {
        Log.e(TAG, "saveUser: currency : ${ currency.toString() }", )
        return userDao.saveUser(UserEntity(id, currency))
    }

    private fun getCountryCode(): String {
        val countryCode = userDao.getCountryCode() ?: resourceProvider.getLocale().country
        Log.e(TAG, "getCountryCode: ${ countryCode.toString() }", )
        return countryCode
    }

    private suspend fun saveWishList(list: List<SteamWishListEntity>) {
        steamWishListDao.deleteWishList()
        steamWishListDao.saveWishList(list)
    }

    fun getListOfGames(): Flow<List<GameEntity>> {
        return gamesDao.getFlowOfAllGames()
    }

    fun getWishList(): Flow<List<GameEntity>> {
        return gamesDao.getSteamWishListFlow()
    }


}