package com.safeboda.domain.usecases

import androidx.paging.PagingData
import com.safeboda.domain.models.FollowerOrFollowingModel
import com.safeboda.domain.repositories.FollowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowingUseCase @Inject constructor(
    private val followRepository: FollowRepository
) {
    suspend operator fun invoke(userName: String): Flow<PagingData<FollowerOrFollowingModel>> {
        return followRepository.getFollowing(userName)
    }
}