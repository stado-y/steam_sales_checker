package com.example.saleschecker.data.local.steamspy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SteamSpyTopListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTopList(list: List<SteamSpyTopListEntity>)

    @Query("DELETE FROM ${SteamSpyTopListEntity.STEAM_SPY_TOP_TABLE}")
    suspend fun deleteTopList()

    @Query("SELECT * FROM ${ SteamSpyTopListEntity.STEAM_SPY_TOP_TABLE }")
    fun getTopList(): List<Int>
}