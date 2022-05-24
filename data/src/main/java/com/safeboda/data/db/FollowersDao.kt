package com.safeboda.data.db

import androidx.room.Dao
import androidx.room.Query
import com.safeboda.data.models.Followers

@Dao
interface FollowersDao {

    @Query("SELECT * FROM followers WHERE login =:login")
    suspend fun getFollowersOfAUser(login: String): List<Followers>
}