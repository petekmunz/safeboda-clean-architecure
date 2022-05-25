package com.safeboda.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.safeboda.data.models.Followers
import com.safeboda.data.models.Following

@Dao
interface FollowersDao {

    @Query("SELECT * FROM followers WHERE parentUsername =:login")
    fun getFollowersOfAUser(login: String): PagingSource<Int, Followers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFollowers(list: List<Following>)

    @Query("DELETE FROM followers WHERE parentUsername = :login")
    suspend fun deleteFollowersOfAUser(login: String)
}