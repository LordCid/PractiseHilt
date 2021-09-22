package com.example.someapp.data

import com.example.someapp.domain.DataModel

interface NetworkDataSource {
    suspend fun getDataList(): Result<List<DataModel>>
    suspend fun getData(id: Long): Result<DataModel>
}