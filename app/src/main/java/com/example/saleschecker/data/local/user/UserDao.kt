package com.example.saleschecker.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("SELECT id FROM ${UserEntity.TABLE_NAME}")
    fun getUserId(): Long

    @Query("SELECT countryCode FROM ${UserEntity.TABLE_NAME}")
    fun getCountryCode(): String
}