package com.safeboda.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.safeboda.data.models.*

@Database(
    entities = [UserDatabaseModel::class, Followers::class, Following::class, FollowersRemoteKey::class, FollowingRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class SafeBodaDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun followersDao(): FollowersDao
    abstract fun followingDao(): FollowingDao
    abstract fun followersRemoteKeyDao(): FollowersRemoteKeysDao
    abstract fun followingRemoteKeyDao(): FollowingRemoteKeyDao
}