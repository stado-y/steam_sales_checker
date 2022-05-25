package com.example.saleschecker.data.network.steam

import android.util.Log
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.data.local.games.GamesDao
import com.example.saleschecker.data.local.steam.SteamWishListDao
import com.example.saleschecker.data.local.steam.SteamWishListEntity
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.data.local.user.UserEntity
import com.example.saleschecker.mutual.Sorting
import com.example.saleschecker.utils.ResourceProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val TAG = "SteamRepository"
const val UPDATE_QUERY_DELAY = 100L

class SteamRepository @Inject constructor (
    private val steamApi: SteamApiClient,
    private val userDao: UserDao,
    private val gamesDao: GamesDao,
    private val steamWishListDao: SteamWishListDao,
    private val resourceProvider: ResourceProvider,
) {
    suspend fun updateWishList(): Boolean {
        var page = 0
        val userId = userDao.getUserId()
        val countryCode = getCountryCode()

        var response: Map<String, SteamResponseGame>? = userId?.let { id ->
            steamApi.getWishlist(
                profileId = id,
                countryCode = countryCode,
                page = page,
            )
        } ?: return false

        Log.e(TAG, "updateWishList: wishlist response : ${response.toString()}", )
        Log.e(TAG, "updateWishList: wishlist response size : ${response?.size}", )

        if (response.isNullOrEmpty()) { return false }

        steamWishListDao.deleteWishList()

        do {
            convertAndSaveWishlistGames(response)
            delay(UPDATE_QUERY_DELAY)
            response = steamApi.getWishlist(
                profileId = userId,
                countryCode = countryCode,
                page = ++page,
            )
            Log.e(TAG, "updateWishList: wishlist response : ${response.toString()}", )
            Log.e(TAG, "updateWishList: wishlist response size : ${response?.size}", )
            Log.e(TAG, "updateWishList: wishlist page : ${ page }", )

        } while (!response.isNullOrEmpty())

        Log.e(TAG, "updateWishList: returned true", )

        return true
    }

    private suspend fun convertAndSaveWishlistGames(response: Map<String, SteamResponseGame>?) {
        response?.let {
            val convertedGames: ArrayList<GameEntity> = arrayListOf()
            val wishList: ArrayList<SteamWishListEntity> = arrayListOf()

            val currency = resourceProvider.getCurrency(getCountryCode())

            it.forEach { entry ->
                val gameId = entry.key.toInt()
                convertedGames.add(
                    entry.value.convertToGameEntity(
                        gameId,
                        currency,
                    )
                )
                wishList.add(SteamWishListEntity(gameId))
            }
            saveWishList(wishList.toList())
            gamesDao.saveSteamGames(convertedGames.toList())
        }
    }

    suspend fun saveUser(
        id: Long? = userDao.getUserId(),
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
        //steamWishListDao.deleteWishList()
        steamWishListDao.saveWishList(list)
    }

    fun getListOfGames(): Flow<List<GameEntity>> {
        return gamesDao.getFlowOfAllGames()
    }

    fun getWishList(sortingType: Int = Sorting.DISCOUNT): Flow<List<GameEntity>> {
        return gamesDao.getSteamWishListFlow(sortingType)
    }


}