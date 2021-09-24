package com.example.someapp.domain

interface GetDataUseCase {
    suspend operator fun invoke(id: Long): ResultState<DataModel>
}