package com.example.saleschecker.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.saleschecker.data.local.EntityConverters
import com.example.saleschecker.data.local.games.GamesDao
import com.example.saleschecker.data.local.steam.SteamWishListDao
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListDao
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.data.network.steam.SteamApiClient
import com.example.saleschecker.data.network.steam.SteamResponsePriceUpdate
import com.example.saleschecker.mutual.Constants
import com.example.saleschecker.services.TAG
import com.example.saleschecker.utils.ResourceProvider
import com.example.saleschecker.utils.toCsv
import com.google.common.util.concurrent.ListenableFuture

import dagger.assisted.Assisted

import dagger.assisted.AssistedInject
import java.lang.Exception
import java.lang.IllegalArgumentException


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
            savePrices(getUpdatedPrices(getGamesList(countryCode), countryCode))
            Result.success()
        } catch (error: Exception) {
            Log.e(TAG, "doWork: ", error)
            Result.failure()
        }



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
        return steamApiClient.getAppDetails(
            gameIds.toCsv(),
            countryCode,
        )
    }

    private suspend fun savePrices(response: Map<String, SteamResponsePriceUpdate>) {
        gamesDao.updatePrices(EntityConverters.convertSteamResponsePriceUpdateToSteamPriceUpdateEntity(response))
    }

}