package com.example.someapp.domain

sealed class ResultState<out T> {
    class Success<T>(val data: T): ResultState<T>()
    object Error: ResultState<Nothing>()
}