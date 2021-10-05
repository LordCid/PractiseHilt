package com.example.someapp.domain

sealed class ResultState<out T> {
    class Success<T>(val data: T) : ResultState<T>()
    object Error : ResultState<Nothing>()
}


inline fun <T> ResultState<T>.fold(onSuccess: (T) -> Unit, onError: () -> Unit) {
    when (this) {
        is ResultState.Success -> onSuccess(data)
        is ResultState.Error -> onError()
    }
}