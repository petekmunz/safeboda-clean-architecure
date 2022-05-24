package com.safeboda.domain.models

data class GithubUser(
    val userName: String,
    val name: String,
    val company: String,
    val location: String,
    val email: String,
    val bio: String,
    val publicRepos: Int,
    val followers: Int,
    val following: Int,
    val avatarUrl: String
)