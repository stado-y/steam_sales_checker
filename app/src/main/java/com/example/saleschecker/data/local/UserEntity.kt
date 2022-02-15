package com.example.saleschecker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.saleschecker.data.local.UserEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class UserEntity(
    @PrimaryKey val id: Long,
) {
    companion object {
        const val TABLE_NAME = "user_table"
    }
}