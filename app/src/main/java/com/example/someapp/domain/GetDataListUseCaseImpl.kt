package com.example.someapp.domain

import com.example.someapp.data.NetworkDataSource


class GetDataListUseCaseImpl(
    private val networkDataSourceImpl: NetworkDataSource
) : GetDataListUseCase {
    override suspend fun invoke() = networkDataSourceImpl.getDataList()
}