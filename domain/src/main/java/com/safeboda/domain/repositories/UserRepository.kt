package com.safeboda.domain.repositories

import com.safeboda.domain.Resource
import com.safeboda.domain.models.GithubUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getuser(username: String): Flow<Resource<GithubUser?>>
}