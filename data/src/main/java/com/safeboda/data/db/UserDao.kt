package com.safeboda.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.safeboda.data.models.UserServerModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userServerModel: UserServerModel): Long

    @Query("SELECT * FROM user WHERE login =:username")
    suspend fun getUser(username: String): UserServerModel?
}