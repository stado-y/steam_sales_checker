package com.example.saleschecker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.data.local.games.GamesDao
import com.example.saleschecker.data.local.steam.SteamWishListDao
import com.example.saleschecker.data.local.steam.SteamWishListEntity
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListDao
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListEntity
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.data.local.user.UserEntity


@Database(
    entities = [
        GameEntity::class,
        UserEntity::class,
        SteamWishListEntity::class,
        SteamSpyTopListEntity::class
    ],
    version = 1,
)
@TypeConverters(EntityConverters::class)
abstract class SalesDatabase : RoomDatabase() {

    abstract fun gamesDao(): GamesDao
    abstract fun userDao(): UserDao
    abstract  fun steamWishListDao(): SteamWishListDao
    abstract fun steamSpyTopListDao(): SteamSpyTopListDao

    companion object {
        const val DATABASE_NAME = "sales_checker_db"
    }
}