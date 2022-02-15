package com.example.saleschecker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [GameEntity::class, UserEntity::class],
    version = 1,
)
abstract class SalesDatabase : RoomDatabase() {

    abstract fun gamesDao(): GamesDao
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "sales_checker_db"
    }
}