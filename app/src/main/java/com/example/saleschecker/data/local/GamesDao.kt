package com.example.saleschecker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSteamGames(games: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveSteamSpyGames(toList: List<GameEntity>)

    @Query("SELECT * FROM ${ GameEntity.TABLE_NAME }")
    fun getListOfAllGames(): Flow<List<GameEntity>>



}