package com.example.someapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.someapp.data
import com.example.someapp.domain.DataModel
import com.example.someapp.domain.GetDataListUseCase
import com.example.someapp.domain.ResultState
import com.example.someapp.otherData
import com.example.someapp.presentation.list.ListViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {

    private lateinit var sut: ListViewModel
    private val getDataListUseCase = mock<GetDataListUseCase>()
    private val observer = mock<Observer<ListViewState>>()
    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    private val captorScreenState = argumentCaptor<ListViewState>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        sut =  ListViewModel(getDataListUseCase, dispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Should show Loading screen when invoke use case`() {
        runBlocking {
            val expected = ViewState.Loading

            sut.viewState.observeForever(observer)
            sut.getDataList()

            verify(getDataListUseCase).invoke()
            verify(observer).onChanged(expected)

        }
    }

    @Test
    fun `Given data list get, it is shown into UI`() {
        runBlocking {
            val expectedList = listOf(data, data)
            givenSuccessResultWithValues(expectedList)

            sut.viewState.observeForever(observer)
            sut.getDataList()

            verify(observer, times(2)).onChanged(captorScreenState.capture())
            val capturedState = captorScreenState.secondValue as ViewState.ShowData
            assertEquals(expectedList, capturedState.data)
        }
    }

    @Test
    fun `Given OTHER group list getted, it is shown into UI`() {
        runBlocking {
            val expectedList =  listOf(otherData, otherData)
            givenSuccessResultWithValues(expectedList)

            sut.viewState.observeForever(observer)
            sut.getDataList()

            verify(observer, times(2)).onChanged(captorScreenState.capture())
            val capturedState = captorScreenState.secondValue as ViewState.ShowData
            assertEquals(expectedList, capturedState.data)
        }
    }

    @Test
    fun `Given failure when getting group list, error is shown in the UI`() {
        runBlocking {
            givenFailureResult()

            sut.viewState.observeForever(observer)
            sut.getDataList()

            verify(observer, times(2)).onChanged(captorScreenState.capture())
            assert(captorScreenState.secondValue is ViewState.Error)
        }
    }

    private suspend fun givenSuccessResultWithValues(list: List<DataModel>) {
        given(getDataListUseCase.invoke()).willReturn(ResultState.Success(list))
    }

    private suspend fun givenFailureResult() {
        given(getDataListUseCase.invoke()).willReturn(ResultState.Error)
    }
}