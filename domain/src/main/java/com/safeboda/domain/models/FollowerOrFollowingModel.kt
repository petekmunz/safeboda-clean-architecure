package com.safeboda.domain.models

data class FollowerOrFollowingModel(
    var id: Long,
    val parentUsername: String,
    val username: String,
    val avatarUrl: String,
)