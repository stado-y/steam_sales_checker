package com.example.saleschecker.di

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.saleschecker.mutual.Constants
import com.example.saleschecker.workers.SteamPriceUpdateWorker
import com.example.saleschecker.workers.UPDATE_PRICE_WORK_TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerCreator @Inject constructor(
    @ApplicationContext private val appContext: Context,
) {

    fun createPriceUpdater(workType: String, context: Context = appContext) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()

//        val inputData = Data.Builder()
//            .putString(Constants.TYPE_OF_UPDATE, workType)
//            .build()

        val request = OneTimeWorkRequestBuilder<SteamPriceUpdateWorker>()
            .addTag(UPDATE_PRICE_WORK_TAG)
            .setInputData(workDataOf(Constants.TYPE_OF_UPDATE to workType))
            .setConstraints(constraints)
            .build()


        WorkManager.getInstance(context).enqueue(request)
    }

}
