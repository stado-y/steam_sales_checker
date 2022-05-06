package com.example.saleschecker.data.local.steam

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SteamWishListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWishList(list: List<SteamWishListEntity>)

    @Query("DELETE FROM ${SteamWishListEntity.STEAM_WISHLIST_TABLE}")
    suspend fun deleteWishList()

    @Query("SELECT `gameId` FROM ${SteamWishListEntity.STEAM_WISHLIST_TABLE}")
    suspend fun getSteamWishListGameIds(): List<Int>
}