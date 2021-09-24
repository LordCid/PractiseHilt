package com.example.someapp.domain


interface GetDataListUseCase {
    suspend operator fun invoke(): ResultState<List<DataModel>>
}