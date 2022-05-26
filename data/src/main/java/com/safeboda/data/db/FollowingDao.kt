package com.safeboda.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.safeboda.data.models.Following

@Dao
interface FollowingDao {

    @Query("SELECT * FROM following WHERE parentUsername =:login")
    fun getFollowingOfAUser(login: String): PagingSource<Int, Following>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFollowing(list: List<Following>)

    @Query("DELETE FROM following WHERE parentUsername = :login")
    suspend fun deleteFollowingOfAUser(login: String)
}