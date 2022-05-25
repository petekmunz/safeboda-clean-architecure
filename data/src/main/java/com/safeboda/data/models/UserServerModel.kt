package com.safeboda.data.models

import com.google.gson.annotations.SerializedName

data class UserServerModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val userName: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("public_repo")
    val publicRepos: Int,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("twitter_username")
    val twitterUserName: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)