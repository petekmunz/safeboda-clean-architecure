package com.safeboda.data.mappers

import com.safeboda.data.models.Followers
import com.safeboda.data.models.Following
import com.safeboda.data.models.UserDatabaseModel
import com.safeboda.data.models.UserServerModel
import com.safeboda.domain.models.FollowerOrFollowingModel
import com.safeboda.domain.models.GithubUser

class UserMapper {
    fun toUserDatabaseModel(userServerModel: UserServerModel): UserDatabaseModel {
        return UserDatabaseModel(
            userName = userServerModel.userName ?: "",
            name = userServerModel.name ?: "",
            company = userServerModel.company ?: "",
            location = userServerModel.location ?: "",
            email = userServerModel.email ?: "",
            bio = userServerModel.bio ?: "",
            publicRepos = userServerModel.publicRepos,
            followers = userServerModel.followers,
            following = userServerModel.following,
            avatarUrl = userServerModel.avatarUrl ?: "",
            id = userServerModel.id
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

    fun toGithubUser(userDatabaseModel: UserDatabaseModel): GithubUser {
        return GithubUser(
            userName = userDatabaseModel.userName,
            name = userDatabaseModel.name,
            company = userDatabaseModel.company,
            location = userDatabaseModel.location,
            email = userDatabaseModel.email,
            bio = userDatabaseModel.bio,
            publicRepos = userDatabaseModel.publicRepos,
            followers = userDatabaseModel.followers,
            following = userDatabaseModel.following,
            avatarUrl = userDatabaseModel.avatarUrl
        )
    }

    fun toFollowerOrFollowing(following: Following): FollowerOrFollowingModel {
        return FollowerOrFollowingModel(
            id = following.id,
            parentUsername = following.parentUsername,
            username = following.username,
            name = following.name,
            avatarUrl = following.avatarUrl,
            bio = following.bio
        )
    }

    fun toFollowerOrFollowing(followers: Followers): FollowerOrFollowingModel {
        return FollowerOrFollowingModel(
            id = followers.id,
            parentUsername = followers.parentUsername,
            username = followers.username,
            name = followers.name,
            avatarUrl = followers.avatarUrl,
            bio = followers.bio
        )
    }
}