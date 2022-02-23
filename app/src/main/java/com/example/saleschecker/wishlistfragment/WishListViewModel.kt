package com.example.saleschecker.wishlistfragment

import android.util.Log
import androidx.lifecycle.asLiveData
import com.example.saleschecker.data.local.GameEntity
import com.example.saleschecker.data.network.steam.SteamRepository
import com.example.saleschecker.mutual.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

const val TAG = "WishListViewModel"

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val repository: SteamRepository
): BaseViewModel() {

    val games: Flow<List<GameEntity>> = repository.getListOfGames()



    init {
        execute( request = { repository.updateWishList() })
    }

    override fun showError(error: Throwable?) {
        Log.e(TAG, "showError: ", error)
//        when (error) {
//            is HttpException ->
//        }
    }
}