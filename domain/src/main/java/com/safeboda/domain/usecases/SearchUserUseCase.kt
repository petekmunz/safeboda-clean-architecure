package com.safeboda.domain.usecases

import com.safeboda.domain.Resource
import com.safeboda.domain.models.GithubUser
import com.safeboda.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userName: String): Flow<Resource<GithubUser?>> {
        return userRepository.getuser(userName)
    }
}