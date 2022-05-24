package com.safeboda.data.mappers

import com.safeboda.data.models.Followers
import com.safeboda.data.models.Following
import com.safeboda.data.models.UserAppModel
import com.safeboda.data.models.UserServerModel
import javax.inject.Inject

class UserMapper @Inject constructor() {
    fun toUserAppModel(userServerModel: UserServerModel): UserAppModel {
        return UserAppModel(
            userName = userServerModel.userName ?: "",
            name = userServerModel.name ?: "",
            company = userServerModel.company ?: "",
            location = userServerModel.location ?: "",
            email = userServerModel.email ?: "",
            bio = userServerModel.bio ?: "",
            publicRepos = userServerModel.publicRepos,
            followers = userServerModel.followers,
            following = userServerModel.following,
            avatarUrl = userServerModel.avatarUrl ?: ""
        )
    }

    fun toFollower(parentUsername: String, userServerModel: UserServerModel): Followers {
        return Followers(
            id = 0,
            username = userServerModel.userName ?: "",
            name = userServerModel.name ?: "",
            avatarUrl = userServerModel.avatarUrl ?: "",
            bio = userServerModel.bio ?: "",
            parentUsername = parentUsername
        )
    }

    fun toFollowing(parentUsername: String, userServerModel: UserServerModel): Following {
        return Following(
            id = 0,
            parentUsername = parentUsername,
            username = userServerModel.userName ?: "",
            name = userServerModel.name ?: "",
            avatarUrl = userServerModel.avatarUrl ?: "",
            bio = userServerModel.bio ?: ""
        )
    }
}