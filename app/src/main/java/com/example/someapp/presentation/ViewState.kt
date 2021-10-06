package com.example.someapp.presentation

import com.example.someapp.domain.DataModel

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    class ShowData<T>(val data: T) : ViewState<T>()
    object Error : ViewState<Nothing>()
}

typealias ListViewState = ViewState<List<DataModel>>
typealias DetailViewState = ViewState<DataModel>