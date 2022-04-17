package com.example.saleschecker.di

import android.content.Context
import androidx.room.Room
import com.example.saleschecker.data.local.games.GamesDao
import com.example.saleschecker.data.local.SalesDatabase
import com.example.saleschecker.data.local.user.UserDao
import com.example.saleschecker.data.local.steam.SteamWishListDao
import com.example.saleschecker.data.local.steamspy.SteamSpyTopListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomDbModule {



    @Provides
    @Singleton
    fun provideGamesDao(db: SalesDatabase): GamesDao {
        return db.gamesDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: SalesDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideSteamWishListDao(db: SalesDatabase): SteamWishListDao {
        return db.steamWishListDao()
    }

    @Provides
    @Singleton
    fun provideSteamSpyTopListDao(db: SalesDatabase): SteamSpyTopListDao {
        return db.steamSpyTopListDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): SalesDatabase {
        return Room.databaseBuilder(
            appContext,
            SalesDatabase::class.java,
            SalesDatabase.DATABASE_NAME,
        ).build()
    }
}