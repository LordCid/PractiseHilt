package com.example.someapp.presentation.detail

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.someapp.R
import com.example.someapp.databinding.FragmentDetailBinding
import com.example.someapp.databinding.FragmentListBinding
import com.example.someapp.domain.DataModel
import com.example.someapp.presentation.ViewState
import com.example.someapp.presentation.list.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    private var _bindingView: FragmentDetailBinding? = null
    private val bindingView get() = _bindingView!!

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingView = FragmentDetailBinding.inflate(layoutInflater)
        return bindingView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(context)
        setViewModel()
    }

    private fun setViewModel(){
        viewModel.viewState.observe(::getLifecycle, ::updateUI)
        viewModel.getData(args.id)
    }

    private fun updateUI(viewState: ViewState<DataModel>) {
        when (viewState) {
            is ViewState.ShowData -> showData(viewState.data)
            ViewState.Error -> showErrorMessage()
            ViewState.Loading -> showLoadingDialogFragment()
        }
    }

    private fun showData(data: DataModel) {
        progressDialog.dismiss()
        with(bindingView){
            title.text = data.title
            body.text = data.body
        }
    }

    private fun showLoadingDialogFragment() {
        progressDialog.setMessage(getString(R.string.downloading_title_dialog))
        progressDialog.show()
    }

    private fun showErrorMessage() {
        with(bindingView){
            content.visibility = View.GONE
            error.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _bindingView = null
    }
}