package com.example.saleschecker.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.saleschecker.data.local.EntityConverters
import com.example.saleschecker.data.local.games.GamesDao
import com.example.saleschecker.data.local.steam.SteamPriceUpdateEntity
import com.example.saleschecker.data.local.steam.SteamWishListDao
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListDao
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.data.network.steam.SteamApiClient
import com.example.saleschecker.data.network.steam.SteamResponsePriceUpdate
import com.example.saleschecker.mutual.Constants
import com.example.saleschecker.utils.ResourceProvider
import com.example.saleschecker.utils.toCsv
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception
import java.lang.IllegalArgumentException

const val TAG = "price_update_worker"

const val UPDATE_PRICE_WORK_TAG = "SteamPriceUpdateWorker"
@HiltWorker
class SteamPriceUpdateWorker @AssistedInject constructor(
    @Assisted context: Context, @Assisted params: WorkerParameters,
    private val steamApiClient: SteamApiClient,
    private val gamesDao: GamesDao,
    private val userDao: UserDao,
    private val steamWishListDao: SteamWishListDao,
    private val resourceProvider: ResourceProvider,
    private val steamSpyTopListDao: SteamSpyTopListDao,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val countryCode = userDao.getCountryCode() ?: resourceProvider.getLocale().country

            val listOfGameIds = getGamesList(countryCode)
            Log.e(TAG, "doWork: listOfGameIds size : ${ listOfGameIds.size }", )

            val response = convertResponse(getUpdatedPrices(listOfGameIds, countryCode)).toMutableList()
            val newCurrency = resourceProvider.getCurrency(countryCode)
            response += manuallyUpdateGames(getListDiff(listOfGameIds, response), newCurrency)
            savePrices(response)

            Result.success()
        } catch (error: Exception) {
            Log.e(TAG, "doWork: ", error)
            Result.failure()
        }
    }

    private fun manuallyUpdateGames(listDiff: List<Int>, newCurrency: String): List<SteamPriceUpdateEntity> {
        val notUpdatedGames = gamesDao.getListOfGamesByListOfIds(listDiff)

        val manuallyUpdatedGames: List<SteamPriceUpdateEntity> = notUpdatedGames.mapNotNull {
            SteamPriceUpdateEntity(
                id = it.id,
                currency = newCurrency,
                price = if (it.is_free_game) 0f else Constants.DEFAULT_PRICE,
                discount_percent = 0,
            )
        }
        Log.e(TAG, "doWork: manually updated : ${manuallyUpdatedGames}",)
        return manuallyUpdatedGames
    }

    private fun getListDiff(
        listOfGameIds: List<Int>,
        response: List<SteamPriceUpdateEntity>
    ): List<Int> {
        val responseIds = response.map {
            it.id
        }
        return listOfGameIds - responseIds
    }

    private suspend fun getGamesList(countryCode: String): List<Int> {
        val workType = inputData.getString(Constants.TYPE_OF_UPDATE)
        return when (workType) {
            Constants.UPDATE_WISHLIST -> steamWishListDao.getSteamWishListGameIds()
            Constants.UPDATE_CURRENCY -> gamesDao.getListOfGameIdsToUpdateCurrency(resourceProvider.getCurrency(countryCode))
            Constants.UPDATE_STEAMSPY_PRICES -> steamSpyTopListDao.getTopList()
            else -> throw (IllegalArgumentException("$TAG : getGamesList : invalid update type : $workType"))
        }
    }

    private suspend fun getUpdatedPrices(
        gameIds: List<Int>,
        countryCode: String
    ): Map<String, SteamResponsePriceUpdate> {
        return steamApiClient.getAppDetailsBulk(
            gameIds.toCsv(),
            countryCode,
        ).filter {
            it.value.success && it.value.data.isNotEmpty()
        }
    }

    private suspend fun savePrices(list: List<SteamPriceUpdateEntity>) {
        gamesDao.updatePrices(list)
    }

    private fun convertResponse(
        response: Map<String, SteamResponsePriceUpdate>
    ): List<SteamPriceUpdateEntity> {
        return EntityConverters.convertSteamResponsePriceUpdateToSteamPriceUpdateEntity(response)
    }

}