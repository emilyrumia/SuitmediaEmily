package com.example.suitmediaapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.suitmediaapp.api.ApiService
import com.example.suitmediaapp.api.ResultState
import com.example.suitmediaapp.api.response.UserResponse
import retrofit2.HttpException

class SuitmediaRepository private constructor(
    private val apiService: ApiService
) {

    private var currentPage = 1

    fun getUser(page: Int, perPage: Int): LiveData<ResultState<UserResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getUser(page.toString(), perPage.toString())
            if (response.data.isEmpty()) {
                emit(ResultState.Empty("No data available"))
            } else {
                emit(ResultState.Success(response))
            }
        } catch (e: HttpException) {
            emit(ResultState.Error("An error occurred"))
        }
    }

    fun refreshData(perPage: Int) {
        currentPage = 1
        getUser(currentPage, perPage)
    }

    companion object {
        @Volatile
        private var instance: SuitmediaRepository? = null
        fun getInstance(
            apiService: ApiService
        ): SuitmediaRepository =
            instance ?: synchronized(this) {
                instance ?: SuitmediaRepository(apiService)
            }.also { instance = it }
    }
}
