package com.safeboda.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.safeboda.data.models.UserServerModel

@Database(
    entities = [UserServerModel::class],
    version = 1,
    exportSchema = false
)
abstract class SafeBodaDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun followersDao(): FollowersDao
    abstract fun followingDao(): FollowingDao
}