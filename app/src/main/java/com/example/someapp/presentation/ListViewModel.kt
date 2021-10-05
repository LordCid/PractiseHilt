package com.example.someapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.someapp.domain.GetDataListUseCase
import com.example.someapp.domain.fold
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.contracts.ExperimentalContracts

class ListViewModel(
    private val ioDispatcher: CoroutineDispatcher,
    private val getDataListUseCase: GetDataListUseCase
) : ViewModel() {

    private val _viewState: MutableLiveData<ListViewState> = MutableLiveData()
    val viewState: LiveData<ListViewState>
        get() = _viewState

    fun getDataList(){
        viewModelScope.launch {
            _viewState.value = ViewState.Loading
            val results = withContext(ioDispatcher) { getDataListUseCase() }
            results.fold(
                onSuccess = {
                    _viewState.value = ViewState.ShowData(it)
                },
                onError = {
                    _viewState.value = ViewState.Error

                }
            )
        }
    }
}