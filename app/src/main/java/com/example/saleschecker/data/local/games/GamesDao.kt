package com.example.saleschecker.data.local.games

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.saleschecker.data.local.steam.SteamWishListEntity
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSteamGames(games: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveSteamSpyGames(toList: List<GameEntity>)

    @Query("SELECT * FROM ${GameEntity.TABLE_NAME}")
    fun getListOfAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM ${ GameEntity.TABLE_NAME } WHERE `id` IN ${ SteamWishListEntity.STEAM_WISHLIST_TABLE }")
    fun getSteamWishList(): Flow<List<GameEntity>>

    @Query("SELECT * FROM ${ GameEntity.TABLE_NAME } WHERE `id` IN ${ SteamSpyTopListEntity.STEAM_SPY_TOP_TABLE }")
    fun getSteamSpyTopList(): Flow<List<GameEntity>>
}