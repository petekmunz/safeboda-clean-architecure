package com.munyao.safeboda.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.safeboda.domain.Resource
import com.safeboda.domain.models.FollowerOrFollowingModel
import com.safeboda.domain.models.GithubUser
import com.safeboda.domain.usecases.GetFollowersUseCase
import com.safeboda.domain.usecases.GetFollowingUseCase
import com.safeboda.domain.usecases.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val followingUseCase: GetFollowingUseCase,
    private val followersUseCase: GetFollowersUseCase,
    private val userUseCase: SearchUserUseCase
) : ViewModel() {

    private val searchedUserLive: MutableLiveData<Resource<GithubUser?>> = MutableLiveData()

    @OptIn(FlowPreview::class)
    fun searchUser(username: String) {
        viewModelScope.launch {
            userUseCase.invoke(username).debounce(1 * 1000).collectLatest {
                searchedUserLive.value = it
            }
        }
    }

    fun getSearchedUserLive(): LiveData<Resource<GithubUser?>> {
        return searchedUserLive
    }

    suspend fun getFollowers(username: String): Flow<PagingData<FollowerOrFollowingModel>> {
        return followersUseCase.invoke(username).cachedIn(viewModelScope)
    }

    suspend fun getFollowing(username: String): Flow<PagingData<FollowerOrFollowingModel>> {
        return followingUseCase.invoke(username).cachedIn(viewModelScope)
    }
}