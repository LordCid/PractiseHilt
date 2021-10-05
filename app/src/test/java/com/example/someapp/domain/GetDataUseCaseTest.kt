package com.example.someapp.domain

import com.example.someapp.data
import com.example.someapp.data.NetworkDataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetDataUseCaseTest {

    private lateinit var sut: GetDataUseCase
    private val networkDataSource = mock<NetworkDataSource>()

    @Before
    fun setUp() {
        sut = GetDataUseCaseImpl(networkDataSource)
    }

    @Test
    fun `Should invoke get data list in datasource and return its result`() {
        runBlocking {
            val someId = 123L
            val expected = data
            given(networkDataSource.getData(any())).willReturn(ResultState.Success(expected))

            val actual = sut.invoke(someId)

            verify(networkDataSource).getData(someId)
            assertEquals(expected, (actual as ResultState.Success).data)
        }
    }
}