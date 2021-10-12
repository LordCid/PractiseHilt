package com.example.someapp.presentation

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.someapp.R

abstract class BaseFragment: Fragment() {
    private lateinit var progressDialog: ProgressDialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(context)
    }

    protected fun <T> updateUI(viewState: ViewState<T>) {
        when (viewState) {
            is ViewState.ShowData ->{
                progressDialog.dismiss()
                showData(viewState.data)
            }
            ViewState.Error -> {
                progressDialog.dismiss()
                showErrorMessage()
            }
            ViewState.Loading -> showLoadingDialogFragment()
        }
    }

    abstract fun <T> showData(data: T)

    private fun showLoadingDialogFragment() {
        progressDialog.setMessage(getString(R.string.downloading_title_dialog))
        progressDialog.show()
    }

    abstract fun showErrorMessage()
}