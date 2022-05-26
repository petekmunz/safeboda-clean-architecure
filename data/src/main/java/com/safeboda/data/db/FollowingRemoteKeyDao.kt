package com.safeboda.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.safeboda.data.models.FollowingRemoteKeys

@Dao
interface FollowingRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<FollowingRemoteKeys>)

    @Query("SELECT * FROM following_remote_key WHERE followingId = :followingId")
    suspend fun remoteKeysFollowingId(followingId: Long): FollowingRemoteKeys?

    @Query("DELETE FROM following_remote_key")
    suspend fun clearRemoteKeys()
}