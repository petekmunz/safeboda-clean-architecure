package com.safeboda.data.repo.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.safeboda.data.Constants
import com.safeboda.data.apiservice.ApiService
import com.safeboda.data.db.FollowingDao
import com.safeboda.data.db.FollowingRemoteKeyDao
import com.safeboda.data.db.SafeBodaDatabase
import com.safeboda.data.mappers.UserMapper
import com.safeboda.data.models.Following
import com.safeboda.data.models.FollowingRemoteKeys
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FollowingRemoteMediator @Inject constructor(
    private val username: String,
    private val followingDao: FollowingDao,
    private val followingRemoteKeyDao: FollowingRemoteKeyDao,
    private val dB: SafeBodaDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, Following>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Following>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND ->{
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                    prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            val response = apiService.getFollowing(
                username = username,
                perPage = Constants.NETWORK_PAGE_SIZE,
                page = loadKey
            )
            val following = response.body()
            val endOfPaginationReached = following.isNullOrEmpty()

            dB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    followingDao.deleteFollowingOfAUser(username)
                }

                val prevKey = if (loadKey == 1) null else loadKey - 1
                val nextKey = if (endOfPaginationReached) null else loadKey + 1

                following?.map {
                    FollowingRemoteKeys(followingId = it.id, prevKey = prevKey, nextKey = nextKey)
                }?.let { followingRemoteKeyDao.insertAll(it) }

                response.body()?.map {
                    UserMapper().toFollowing(username, it)
                }?.let { followingDao.insertAllFollowing(it) }
            }

            MediatorResult.Success(
                endOfPaginationReached = response.body()?.size ?: 0 < Constants.NETWORK_PAGE_SIZE
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Following>
    ): FollowingRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                followingRemoteKeyDao.remoteKeysFollowingId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Following>): FollowingRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { following ->
                followingRemoteKeyDao.remoteKeysFollowingId(following.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Following>): FollowingRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { following ->
                followingRemoteKeyDao.remoteKeysFollowingId(following.id)
            }
    }
}