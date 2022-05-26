package com.safeboda.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDatabaseModel(
    @PrimaryKey
    val id: Long,
    val userName: String,
    val name: String,
    val company: String,
    val location: String,
    val email: String,
    val bio: String,
    val publicRepos: Int,
    val followers: Int,
    val following: Int,
    val avatarUrl: String,
    val twitterUserName: String,
    val blog: String,
    val createdAt: String
)