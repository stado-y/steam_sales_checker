package com.example.saleschecker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserId(user: UserEntity)

    @Query("SELECT id FROM ${ UserEntity.TABLE_NAME }")
    fun getUserId(): Long
}