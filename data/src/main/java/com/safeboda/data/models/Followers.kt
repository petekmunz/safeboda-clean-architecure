package com.safeboda.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followers")
data class Followers(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val parentUsername: String,
    val username: String,
    val name: String,
    val avatarUrl: String,
    val bio: String
)