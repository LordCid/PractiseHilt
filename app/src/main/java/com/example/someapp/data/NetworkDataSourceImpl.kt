package com.example.someapp.data

import com.example.someapp.domain.DataModel
import com.example.someapp.domain.toDomain
import retrofit2.awaitResponse

class NetworkDataSourceImpl(private val apiService: ApiService) : NetworkDataSource {
    override suspend fun getDataList(): Result<List<DataModel>> {
        return runCatching { apiService.getDataList().awaitResponse() }.fold(
            onSuccess = {
                val resultList = it.body()?.let { response ->
                    response.map { netModel -> netModel.toDomain() }
                }.orEmpty()
                Result.success(resultList)
            },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun getData(id: Long): Result<DataModel> {
        return runCatching { apiService.getData(id).awaitResponse() }.fold(
            onSuccess = {
                it.body()?.let { result -> Result.success(result.toDomain()) } ?:Result.failure(Throwable())
            },
            onFailure = { Result.failure(it) }
        )
    }
}