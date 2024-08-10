package com.example.suitmediaapp.di

import android.content.Context
import com.example.suitmediaapp.SuitmediaRepository
import com.example.suitmediaapp.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): SuitmediaRepository {
        val apiService = ApiConfig.getApiService()
        return SuitmediaRepository.getInstance(apiService)
    }
}