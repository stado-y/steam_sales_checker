package com.example.saleschecker.data.local.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("UPDATE ${ UserEntity.TABLE_NAME } SET `id` = :userId")
    suspend fun updateUserId(userId: Long)

    @Query("UPDATE ${ UserEntity.TABLE_NAME } SET `countryCode` = :countryCode")
    suspend fun updateCountryCode(countryCode: String)

    @Query("SELECT id FROM ${UserEntity.TABLE_NAME}")
    fun getUserId(): Long?

    @Query("SELECT id FROM ${UserEntity.TABLE_NAME}")
    fun getUserIdLiveData(): LiveData<Long?>

    @Query("SELECT countryCode FROM ${UserEntity.TABLE_NAME}")
    fun getCountryCode(): String?

    @Query("SELECT countryCode FROM ${ UserEntity.TABLE_NAME }")
    fun getCountryCodeLiveData(): LiveData<String?>
}