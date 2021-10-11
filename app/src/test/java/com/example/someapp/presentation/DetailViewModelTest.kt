package com.example.someapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.someapp.data
import com.example.someapp.domain.DataModel
import com.example.someapp.domain.GetDataUseCase
import com.example.someapp.domain.ResultState
import com.example.someapp.otherData
import com.example.someapp.presentation.detail.DetailViewModel
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

class DetailViewModelTest {

    private lateinit var sut: DetailViewModel
    private val getDataUseCase = mock<GetDataUseCase>()
    private val observer = mock<Observer<DetailViewState>>()
    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    private val captorScreenState = argumentCaptor<DetailViewState>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        sut =  DetailViewModel(dispatcher, getDataUseCase)
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
            val someId = 123L
            val expected = ViewState.Loading

            sut.viewState.observeForever(observer)
            sut.getData(someId)

            verify(getDataUseCase).invoke(someId)
            verify(observer).onChanged(expected)

        }
    }

    @Test
    fun `Given get data success, result is shown into ShowDataState`() {
        runBlocking {
            val someId = 123L
            val expectedList = data
            givenSuccessResultWithValues(expectedList)

            sut.viewState.observeForever(observer)
            sut.getData(someId)

            verify(observer, times(2)).onChanged(captorScreenState.capture())
            val capturedState = captorScreenState.secondValue as ViewState.ShowData
            assertEquals(expectedList, capturedState.data)
        }
    }

    @Test
    fun `Given get OTHER data success, result is shown into ShowDataState`() {
        runBlocking {
            val someId = 456L
            val expectedList = otherData
            givenSuccessResultWithValues(expectedList)

            sut.viewState.observeForever(observer)
            sut.getData(someId)

            verify(observer, times(2)).onChanged(captorScreenState.capture())
            val capturedState = captorScreenState.secondValue as ViewState.ShowData
            assertEquals(expectedList, capturedState.data)
        }
    }

    @Test
    fun `Given failure when getting group list, error is shown in the UI`() {
        runBlocking {
            val someId = 123L
            givenFailureResult()

            sut.viewState.observeForever(observer)
            sut.getData(someId)

            verify(observer, times(2)).onChanged(captorScreenState.capture())
            assert(captorScreenState.secondValue is ViewState.Error)
        }
    }

    private suspend fun givenSuccessResultWithValues(data: DataModel) {
        given(getDataUseCase.invoke(any())).willReturn(ResultState.Success(data))
    }

    private suspend fun givenFailureResult() {
        given(getDataUseCase.invoke(any())).willReturn(ResultState.Error)
    }

}