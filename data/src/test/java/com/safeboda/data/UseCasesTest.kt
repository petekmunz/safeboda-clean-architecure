package com.safeboda.data

import androidx.paging.PagingData
import com.safeboda.domain.Resource
import com.safeboda.domain.models.FollowerOrFollowingModel
import com.safeboda.domain.repositories.FollowRepository
import com.safeboda.domain.repositories.UserRepository
import com.safeboda.domain.usecases.GetFollowersUseCase
import com.safeboda.domain.usecases.GetFollowingUseCase
import com.safeboda.domain.usecases.SearchUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class UseCasesTest {

    private val followRepository = mock<FollowRepository>()
    private val userRepository = mock<UserRepository>()
    private val getFollowersUseCaseTest by lazy { GetFollowersUseCase(followRepository) }
    private val getFollowingUseCaseTest by lazy { GetFollowingUseCase(followRepository) }
    private val searchUserUseCase by lazy { SearchUserUseCase(userRepository) }
    private val mockFollower = FollowerOrFollowingModel(1, "johndoe", "mzee_abdalla", "")


    @Test
    fun `getFollowersUseCase returns paging data successfully`() = runBlocking {
        whenever(followRepository.getFollowers("johndoe"))
            .thenReturn(flowOf(PagingData.from(listOf(mockFollower))))
        val data = getFollowersUseCaseTest.invoke("johndoe").first()
        assertThat(data, `is`(not(equalTo(null))))
    }

    @Test
    fun `getFollowingUseCase returns paging data successfully`() = runTest {
        whenever(followRepository.getFollowing("johndoe"))
            .thenReturn(flowOf(PagingData.from(listOf(mockFollower))))
        val data = getFollowingUseCaseTest.invoke("johndoe").first()
        assertThat(data, `is`(not(equalTo(null))))
    }

    @Test
    fun `searchUserUseCase case success`() = runTest {
        whenever(userRepository.getuser("johndoe"))
            .thenReturn(flowOf(Resource.Success(null)))
        val data = searchUserUseCase.invoke("johndoe").first()
        assertThat(data.message, `is`(equalTo(null)))
    }

    @Test
    fun `searchUserUseCase case error`() = runTest {
        whenever(userRepository.getuser("johndoe"))
            .thenReturn(flowOf(Resource.Error("An exception occurred")))
        val data = searchUserUseCase.invoke("johndoe").first()
        assertThat(data.message, `is`(equalTo("An exception occurred")))
    }

    @Test
    fun `searchUserUseCase case loading`() = runTest {
        whenever(userRepository.getuser("johndoe"))
            .thenReturn(flowOf(Resource.Loading()))
        val data = searchUserUseCase.invoke("johndoe").first()
        val noValues = data.data == null && data.message == null
        assertThat(noValues, `is`(equalTo(true)))
    }
}