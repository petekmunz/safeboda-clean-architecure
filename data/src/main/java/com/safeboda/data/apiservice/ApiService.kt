package com.safeboda.data.apiservice

import com.safeboda.data.models.UserServerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users/{username}")
    suspend fun searchUser(@Path("username") username: String): Response<UserServerModel>

    @GET("/users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<List<UserServerModel>>

    @GET("/users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<List<UserServerModel>>
}