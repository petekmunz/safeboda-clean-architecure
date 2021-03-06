package com.safeboda.data.di

import android.content.Context
import androidx.room.Room
import com.safeboda.data.db.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): SafeBodaDatabase {
        return Room.databaseBuilder(
            appContext,
            SafeBodaDatabase::class.java, "safeboda-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: SafeBodaDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideFollowingDao(database: SafeBodaDatabase): FollowingDao {
        return database.followingDao()
    }

    @Provides
    @Singleton
    fun provideFollowersDao(database: SafeBodaDatabase): FollowersDao {
        return database.followersDao()
    }

    @Provides
    @Singleton
    fun provideFollowersRemoteKeyDao(database: SafeBodaDatabase): FollowersRemoteKeysDao {
        return database.followersRemoteKeyDao()
    }

    @Provides
    @Singleton
    fun provideFollowingRemoteKeyDao(database: SafeBodaDatabase): FollowingRemoteKeyDao {
        return database.followingRemoteKeyDao()
    }
}