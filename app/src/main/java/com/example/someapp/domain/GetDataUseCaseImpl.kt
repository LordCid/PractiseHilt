package com.example.someapp.domain

import com.example.someapp.data.NetworkDataSource
import javax.inject.Inject

class GetDataUseCaseImpl @Inject constructor (
    private val networkDataSource: NetworkDataSource
) : GetDataUseCase {
    override suspend fun invoke(id: Long) = networkDataSource.getData(id)
}