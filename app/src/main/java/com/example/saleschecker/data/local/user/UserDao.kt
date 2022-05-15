package com.example.saleschecker.data.local.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.saleschecker.mutual.Constants

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    suspend fun saveUser(user: UserEntity) {
        if (getSavedUserId() != null) {
            deleteTable()
        }
        insertUser(user)
    }

    @Query("UPDATE ${ UserEntity.TABLE_NAME } SET `id` = :userId")
    suspend fun updateUserId(userId: Long)

    @Query("UPDATE ${ UserEntity.TABLE_NAME } SET `countryCode` = :countryCode")
    suspend fun updateCountryCode(countryCode: String)

    @Query("SELECT id FROM ${UserEntity.TABLE_NAME}")
    fun getSavedUserId(): Long?

    fun getUserId(): Long? {
        var id = getSavedUserId()
        if (id == Constants.DEFAULT_USER_ID) {
            id = null
        }
        return id
    }

    @Query("SELECT id FROM ${UserEntity.TABLE_NAME}")
    fun getUserIdLiveData(): LiveData<Long?>

    @Query("SELECT countryCode FROM ${UserEntity.TABLE_NAME}")
    fun getCountryCode(): String?

    @Query("SELECT countryCode FROM ${ UserEntity.TABLE_NAME }")
    fun getCountryCodeLiveData(): LiveData<String?>

    @Query("DELETE FROM ${ UserEntity.TABLE_NAME }")
    suspend fun deleteTable()
}