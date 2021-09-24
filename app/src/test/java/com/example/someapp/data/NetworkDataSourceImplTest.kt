package com.example.someapp.data

import com.example.someapp.data
import com.example.someapp.domain.ResultState
import com.example.someapp.networkData
import com.example.someapp.otherData
import com.example.someapp.otherNetworkData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.mock.Calls

class NetworkDataSourceImplTest {

    private lateinit var sut: NetworkDataSource
    private val apiService = mock<ApiService>()

    @Before
    fun setUp() {
        sut = NetworkDataSourceImpl(apiService)
    }

    @Test
    fun `When get data list, should invoke proper apiService function`() {
        runBlocking {
            givenNetworkGetDataListResponseOK(emptyList())

            sut.getDataList()

            verify(apiService).getDataList()
        }
    }

    @Test
    fun `When get data, should invoke proper apiService function`() {
        runBlocking {
            val someId = 123L
            givenNetworkGetDataResponseOK(someId, any())

            sut.getData(someId)

            verify(apiService).getData(someId)
        }
    }

    @Test
    fun `Given Success response, when get data list, data is exposed`() {
        runBlocking {
            val expected = listOf(data, data)
            givenNetworkGetDataListResponseOK(listOf(networkData, networkData))

            val actual = sut.getDataList()

            assertEquals(expected, (actual as ResultState.Success).data)
        }
    }

    @Test
    fun `Given OTHER Success response, when get data list, data is exposed`() {
        runBlocking {
            val expected = listOf(otherData, otherData)
            givenNetworkGetDataListResponseOK(listOf(otherNetworkData, otherNetworkData))

            val actual = sut.getDataList()

            assertEquals(expected, (actual as ResultState.Success).data)
        }
    }

    @Test
    fun `Given failure response, when get data list, then return failure`() {
        runBlocking {
            givenNetworkGetDataListResponseKO()

            val actual = sut.getDataList()

            assert(actual is ResultState.Error)
        }
    }

    @Test
    fun `Given Success response, when get data, result is exposed`() {
        runBlocking {
            val expected = data
            val someId = 123L
            givenNetworkGetDataResponseOK(someId, networkData)

            val actual = sut.getData(someId)

            assertEquals(expected, (actual as ResultState.Success).data)
        }
    }

    @Test
    fun `Given OTHER Success response, when get data, result is exposed`() {
        runBlocking {
            val expected = otherData
            val someId = 456L
            givenNetworkGetDataResponseOK(someId, otherNetworkData)

            val actual = sut.getData(someId)

            assertEquals(expected, (actual as ResultState.Success).data)
        }
    }


    @Test
    fun `Given failure response, when get data, then return failure`() {
        runBlocking {
            val someId = 123L
            givenNetworkGetDataResponseKO()

            val actual = sut.getData(someId)

            assert(actual is ResultState.Error)
        }
    }

    private fun givenNetworkGetDataListResponseOK(responseData: List<DataNetworkModel>) {
        given(apiService.getDataList()).willReturn(Calls.response(responseData))
    }


    private fun givenNetworkGetDataListResponseKO() {
        given(apiService.getDataList()).willReturn(Calls.failure(mock()))
    }


    private fun givenNetworkGetDataResponseOK(id: Long, responseData: DataNetworkModel) {
        given(apiService.getData(id)).willReturn(Calls.response(responseData))
    }



    private fun givenNetworkGetDataResponseKO() {
        given(apiService.getData(any())).willReturn(Calls.failure(mock()))
    }
}
