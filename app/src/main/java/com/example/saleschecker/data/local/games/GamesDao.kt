package com.example.saleschecker.data.local.games

import androidx.room.*
import com.example.saleschecker.data.local.steam.SteamPriceUpdateEntity
import com.example.saleschecker.data.local.steam.SteamWishListEntity
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    // steam
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSteamGames(games: List<GameEntity>)

    @Query("SELECT * FROM ${GameEntity.TABLE_NAME} WHERE `id` IN ${SteamWishListEntity.STEAM_WISHLIST_TABLE}")
    fun getSteamWishListFlow(): Flow<List<GameEntity>>

    @Query("SELECT * FROM ${GameEntity.TABLE_NAME} WHERE `id` IN ${SteamWishListEntity.STEAM_WISHLIST_TABLE}")
    fun getSteamWishListGameList(): List<GameEntity>
    // steam

    // steamSpy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveSteamSpyGames(toList: List<GameEntity>)

    @Query("SELECT * FROM ${GameEntity.TABLE_NAME} WHERE `id` IN ${SteamSpyTopListEntity.STEAM_SPY_TOP_TABLE}")
    fun getSteamSpyTopListFlow(): Flow<List<GameEntity>>
    // steamSpy


    @Query("SELECT * FROM ${GameEntity.TABLE_NAME}")
    fun getFlowOfAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM ${GameEntity.TABLE_NAME} WHERE `id` IN (:idList)")
    fun getListOfGamesByListOfIds(idList: List<Int>): List<GameEntity>

    @Query("SELECT `id` FROM ${GameEntity.TABLE_NAME} WHERE `currency` != :currency")
    fun getListOfGameIdsToUpdateCurrency(currency: String): List<Int>

    @Update(entity = GameEntity::class)
    suspend fun updatePrices(prices: List<SteamPriceUpdateEntity>)
}