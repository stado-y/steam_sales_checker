package com.example.saleschecker.homefragment

import androidx.lifecycle.ViewModel
import com.example.saleschecker.data.local.GameEntity
import com.example.saleschecker.data.network.steamspy.SteamSpyRepository
import com.example.saleschecker.mutual.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SteamSpyRepository
) : BaseViewModel() {


    val games: Flow<List<GameEntity>> = repository.getListOfGames()

    init {
        execute( request = { repository.updateTop100in2Weeks() })
    }
}