package com.safeboda.data.repo.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.safeboda.data.apiservice.ApiService
import com.safeboda.data.db.FollowingDao
import com.safeboda.data.db.SafeBodaDatabase
import com.safeboda.data.mappers.UserMapper
import com.safeboda.data.models.Following
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FollowingRemoteMediator @Inject constructor(
    private val username: String,
    private val followingDao: FollowingDao,
    private val dB: SafeBodaDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, Following>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Following>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    lastItem.id
                }
            }

            val response = apiService.getFollowing(
                username = username,
                perPage = 10,
                page = 1
            )

            dB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    followingDao.deleteFollowingOfAUser(username)
                }

                response.body()?.map {
                    UserMapper().toFollowing(username, it)
                }?.let { followingDao.insertAllFollowing(it) }
            }

            MediatorResult.Success(
                endOfPaginationReached = response.body()?.size ?: 0 < 10
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}