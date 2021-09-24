package com.example.someapp.data

import com.example.someapp.domain.DataModel
import com.example.someapp.domain.ResultState

interface NetworkDataSource {
    suspend fun getDataList(): ResultState<List<DataModel>>
    suspend fun getData(id: Long): ResultState<DataModel>
}