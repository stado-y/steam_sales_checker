package com.example.saleschecker.fragments.wishlistfragment

import android.util.Log
import android.widget.Toast
import com.example.saleschecker.R
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.data.network.steam.SteamRepository
import com.example.saleschecker.mutual.BaseViewModel
import com.example.saleschecker.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val TAG = "WishListViewModel"

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val repository: SteamRepository,
    private val resourceProvider: ResourceProvider,
): BaseViewModel() {

    val games: Flow<List<GameEntity>> = repository.getWishList()

    init {
        if (!updated) {
            execute(request = { repository.updateWishList() })
        }
    }

    private fun afterUpdate(updateResult: Boolean) {
        updated = updateResult
        with(resourceProvider) {
            var textId = R.string.update_fail
            if (updateResult) { textId = R.string.update_success }
            showToast(getStringResource(textId), Toast.LENGTH_LONG)
        }

    }

    override fun showError(error: Throwable?) {
        Log.e(TAG, "showError: ", error)
        error?.message?.let { resourceProvider.showToast(it, Toast.LENGTH_LONG) }
//        when (error) {
//            is HttpException ->
//        }
    }

    companion object {
        private var updated = false;
    }
}