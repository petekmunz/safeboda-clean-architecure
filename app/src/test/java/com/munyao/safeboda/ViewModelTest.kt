package com.munyao.safeboda

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.munyao.safeboda.presentation.viewmodels.MainViewModel
import com.safeboda.domain.Resource
import com.safeboda.domain.models.GithubUser
import com.safeboda.domain.usecases.GetFollowersUseCase
import com.safeboda.domain.usecases.GetFollowingUseCase
import com.safeboda.domain.usecases.SearchUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = Dispatchers.Unconfined
    private val userUseCase = mock<SearchUserUseCase>()
    private val followingUseCase = mock<GetFollowingUseCase>()
    private val followersUseCase = mock<GetFollowersUseCase>()
    private val mainViewModel by lazy { MainViewModel(followingUseCase, followersUseCase, userUseCase) }
    private val searchTerm = "johndoe"
    private val mockGithubUser = GithubUser(searchTerm, "", "", "", "", "", 0, 0, 0, "", "", "", "")


    @Before
    fun `set up`(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun `clean up`(){
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `searchUser livedata value updated on search`() = runTest{
        whenever(userUseCase.invoke(searchTerm)).thenReturn(
            flowOf(Resource.Success(mockGithubUser))
        )
        mainViewModel.searchUser(searchTerm)
        val data = mainViewModel.getSearchedUserLive().value
        assertThat(data?.data?.userName, `is`(equalTo(searchTerm)))
    }
}