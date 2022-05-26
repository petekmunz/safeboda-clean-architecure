package com.safeboda.data.repo.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.safeboda.data.Constants
import com.safeboda.data.apiservice.ApiService
import com.safeboda.data.db.FollowersDao
import com.safeboda.data.db.FollowersRemoteKeysDao
import com.safeboda.data.db.SafeBodaDatabase
import com.safeboda.data.mappers.UserMapper
import com.safeboda.data.models.Followers
import com.safeboda.data.models.FollowersRemoteKey
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FollowersRemoteMediator @Inject constructor(
    private val username: String,
    private val followersDao: FollowersDao,
    private val followersRemoteKeysDao: FollowersRemoteKeysDao,
    private val dB: SafeBodaDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, Followers>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Followers>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                    prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            val response = apiService.getFollowers(
                username = username,
                perPage = Constants.NETWORK_PAGE_SIZE,
                page = loadKey
            )
            val followers = response.body()
            val endOfPaginationReached = followers.isNullOrEmpty()

            dB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    followersDao.deleteFollowersOfAUser(username)
                }
                val prevKey = if (loadKey == 1) null else loadKey - 1
                val nextKey = if (endOfPaginationReached) null else loadKey + 1

                followers?.map {
                    FollowersRemoteKey(followersId = it.id, prevKey = prevKey, nextKey = nextKey)
                }?.let { followersRemoteKeysDao.insertAll(it) }

                response.body()?.map {
                    UserMapper().toFollower(username, it)
                }?.let { followersDao.insertAllFollowers(it) }
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
        state: PagingState<Int, Followers>
    ): FollowersRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                followersRemoteKeysDao.remoteKeysFollowersId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Followers>): FollowersRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { following ->
                followersRemoteKeysDao.remoteKeysFollowersId(following.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Followers>): FollowersRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { followers ->
                followersRemoteKeysDao.remoteKeysFollowersId(followers.id)
            }
    }
}