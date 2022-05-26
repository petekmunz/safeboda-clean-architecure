package com.safeboda.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "following_remote_key")
class FollowingRemoteKeys(
    @PrimaryKey
    val followingId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)