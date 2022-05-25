package com.safeboda.data.repo

import com.safeboda.data.apiservice.ApiService
import com.safeboda.data.db.UserDao
import com.safeboda.data.mappers.UserMapper
import com.safeboda.domain.Resource
import com.safeboda.domain.models.GithubUser
import com.safeboda.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepoImplementation @Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getuser(username: String): Flow<Resource<GithubUser?>> {
        return flow {
            emit(Resource.Loading())
            val userLocal = userDao.getUser(username)
            if (userLocal != null) {
                emit(Resource.Success(UserMapper().toGithubUser(userLocal)))
            }
            try {
                val apiResponse = apiService.searchUser(username)
                apiResponse.body()?.let {
                    val user = UserMapper().toUserDatabaseModel(it)
                    userDao.insertUser(user)
                    emit(Resource.Success(UserMapper().toGithubUser(user)))
                }
            } catch (e: HttpException) {
                if (userLocal == null) {
                    emit(
                        Resource.Error(
                            e.localizedMessage ?: "An unexpected error occured"
                        )
                    )
                }
            } catch (e: IOException) {
                if (userLocal == null) {
                    emit(Resource.Error("Couldn't reach server. Check your internet connection."))
                }
            }
        }
    }
}