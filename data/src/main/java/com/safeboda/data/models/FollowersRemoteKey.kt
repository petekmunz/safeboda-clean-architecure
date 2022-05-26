package com.safeboda.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followers_remote_key")
class FollowersRemoteKey(
    @PrimaryKey
    val followersId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)