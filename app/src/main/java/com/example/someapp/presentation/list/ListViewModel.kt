package com.example.someapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.someapp.domain.GetDataListUseCase
import com.example.someapp.domain.fold
import com.example.someapp.presentation.ListViewState
import com.example.someapp.presentation.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getDataListUseCase: GetDataListUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _viewState = MutableLiveData<ListViewState>()
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