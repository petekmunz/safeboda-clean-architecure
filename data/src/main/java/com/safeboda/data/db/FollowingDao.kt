package com.safeboda.data.db

import androidx.room.Dao
import androidx.room.Query
import com.safeboda.data.models.Following

@Dao
interface FollowingDao {

    @Query("SELECT * FROM followers WHERE login =:login")
    suspend fun getFollowingOfAUser(login: String): List<Following>
}