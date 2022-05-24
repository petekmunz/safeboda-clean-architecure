package com.safeboda.data.db

import androidx.room.Database
import com.safeboda.data.models.UserServerModel

@Database(
    entities = [UserServerModel::class],
    version = 1,
    exportSchema = false
)
abstract class SafeBodaDatabase {
    abstract fun userDao(): UserDao
}