package com.example.someapp.domain

import com.example.someapp.data
import com.example.someapp.data.NetworkDataSource
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetDataListUseCaseTest {

    private lateinit var sut: GetDataListUseCase
    private val networkDataSource = mock<NetworkDataSource>()

    @Before
    fun setUp() {
        sut = GetDataListUseCaseImpl(networkDataSource)
    }

    @Test
    fun `Should invoke get data list in datasource and return its result`() {
        runBlocking {
            val expected = listOf(data, data)
            given(networkDataSource.getDataList()).willReturn(ResultState.Success(expected))

            val actual = sut.invoke()

            verify(networkDataSource).getDataList()
            assertEquals(expected, (actual as ResultState.Success).data)
        }
    }
}