package com.example.someapp.domain

import com.example.someapp.data.NetworkDataSource

class GetDataUseCaseImpl (
    private val networkDataSource: NetworkDataSource
) : GetDataUseCase {
    override suspend fun invoke(id: Long) = networkDataSource.getData(id)
}