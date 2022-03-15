package com.example.myapplication.api

import com.example.myapplication.data.SearchResponse
import com.example.myapplication.data.UserDetail
import com.example.myapplication.data.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<Users>>

    @GET("  users/{username}/following")
    fun getFollowings(
        @Path("username") username: String
    ): Call<List<Users>>
}