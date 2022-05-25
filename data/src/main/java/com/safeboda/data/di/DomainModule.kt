package com.safeboda.data.di

import com.safeboda.data.repo.FollowRepoImplementation
import com.safeboda.data.repo.UserRepoImplementation
import com.safeboda.domain.repositories.FollowRepository
import com.safeboda.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsFollowRepository(
        followRepoImpl: FollowRepoImplementation
    ): FollowRepository

    @Binds
    abstract fun bindsUserRepository(
        userRepoImpl: UserRepoImplementation
    ): UserRepository
}