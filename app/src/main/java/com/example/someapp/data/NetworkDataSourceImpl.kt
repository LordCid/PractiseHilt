package com.example.someapp.data

import com.example.someapp.domain.DataModel
import com.example.someapp.domain.ResultState
import com.example.someapp.domain.toDomain
import retrofit2.awaitResponse

class NetworkDataSourceImpl(private val apiService: ApiService) : NetworkDataSource {
    override suspend fun getDataList(): ResultState<List<DataModel>> {
        return runCatching { apiService.getDataList().awaitResponse() }.fold(
            onSuccess = {
                val resultList = it.body()?.let { response ->
                    response.map { netModel -> netModel.toDomain() }
                }.orEmpty()
                ResultState.Success(resultList)
            },
            onFailure = { ResultState.Error }
        )
    }

    override suspend fun getData(id: Long): ResultState<DataModel> {
        return runCatching { apiService.getData(id).awaitResponse() }.fold(
            onSuccess = {
                it.body()?.let { result -> ResultState.Success(result.toDomain()) } ?:ResultState.Error
            },
            onFailure = { ResultState.Error}
        )
    }
}