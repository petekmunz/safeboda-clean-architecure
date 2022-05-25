package com.safeboda.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.safeboda.data.models.Followers
import com.safeboda.data.models.Following
import com.safeboda.data.models.UserDatabaseModel

@Database(
    entities = [UserDatabaseModel::class, Followers::class, Following::class],
    version = 1,
    exportSchema = false
)
abstract class SafeBodaDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun followersDao(): FollowersDao
    abstract fun followingDao(): FollowingDao
}