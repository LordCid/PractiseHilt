package com.example.someapp.domain

import com.example.someapp.data.NetworkDataSource
import javax.inject.Inject


class GetDataListUseCaseImpl @Inject constructor(
    private val networkDataSourceImpl: NetworkDataSource
) : GetDataListUseCase {
    override suspend fun invoke() = networkDataSourceImpl.getDataList()
}