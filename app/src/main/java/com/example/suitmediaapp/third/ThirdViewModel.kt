package com.example.suitmediaapp.third

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.suitmediaapp.SuitmediaRepository
import com.example.suitmediaapp.api.ResultState
import com.example.suitmediaapp.api.response.UserResponse

class ThirdViewModel(private val repository: SuitmediaRepository) : ViewModel() {

    private var perPage = 10
    private var isLoading = false

    fun getUser(page: Int, perPage: Int) = repository.getUser(page, perPage)

    fun refreshData() {
        if (isLoading) return
        isLoading = true
        repository.refreshData(perPage)
        isLoading = false
    }
}
