package com.example.suitmediaapp.api

sealed class ResultState<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: String) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
    data class Empty(val message: String) : ResultState<Nothing>()
}
