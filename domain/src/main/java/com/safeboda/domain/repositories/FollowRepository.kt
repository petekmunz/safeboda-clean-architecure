package com.safeboda.domain.repositories

import androidx.paging.PagingData
import com.safeboda.domain.models.FollowerOrFollowingModel
import kotlinx.coroutines.flow.Flow

interface FollowRepository {
    suspend fun getFollowers(userName: String): Flow<PagingData<FollowerOrFollowingModel>>
    suspend fun getFollowing(userName: String): Flow<PagingData<FollowerOrFollowingModel>>
}