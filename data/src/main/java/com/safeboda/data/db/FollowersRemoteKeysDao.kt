package com.safeboda.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.safeboda.data.models.FollowersRemoteKey

@Dao
interface FollowersRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<FollowersRemoteKey>)

    @Query("SELECT * FROM followers_remote_key WHERE followersId = :followersId")
    suspend fun remoteKeysFollowersId(followersId: Long): FollowersRemoteKey?

    @Query("DELETE FROM followers_remote_key")
    suspend fun clearRemoteKeys()
}