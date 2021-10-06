package com.example.someapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.someapp.domain.GetDataUseCase
import com.example.someapp.domain.fold
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val ioDispatcher: CoroutineDispatcher,
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<DetailViewState>()
    val viewState: LiveData<DetailViewState>
        get() = _viewState

    fun getData(id: Long) {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading
            val results = withContext(ioDispatcher) { getDataUseCase(id) }
            results.fold(
                onSuccess = { _viewState.value = ViewState.ShowData(it) },
                onError = { _viewState.value = ViewState.Error }
            )
        }
    }
}