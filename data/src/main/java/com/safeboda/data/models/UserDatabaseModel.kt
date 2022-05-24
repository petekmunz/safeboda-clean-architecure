package com.safeboda.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserDatabaseModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("login")
    val userName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("public_repo")
    val publicRepos: Int,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String
)