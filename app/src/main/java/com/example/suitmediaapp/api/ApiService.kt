package com.example.suitmediaapp.api

import com.example.suitmediaapp.api.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUser(
        @Query("page") page: String,
        @Query("per_page") offset: String
    ): UserResponse
}