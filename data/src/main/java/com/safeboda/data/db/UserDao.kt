package com.safeboda.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.safeboda.data.models.UserDatabaseModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userServerModel: UserDatabaseModel): Long

    @Query("SELECT * FROM user WHERE username =:username")
    suspend fun getUser(username: String): UserDatabaseModel?
}