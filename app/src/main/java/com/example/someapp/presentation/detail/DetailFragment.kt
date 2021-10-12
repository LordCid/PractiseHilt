package com.example.someapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.someapp.databinding.FragmentDetailBinding
import com.example.someapp.domain.DataModel
import com.example.someapp.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment() {
    private val args: DetailFragmentArgs by navArgs()

    private var _bindingView: FragmentDetailBinding? = null
    private val bindingView get() = _bindingView!!

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingView = FragmentDetailBinding.inflate(layoutInflater)
        return bindingView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
    }

    private fun setViewModel(){
        viewModel.viewState.observe(::getLifecycle, ::updateUI)
        viewModel.getData(args.id)
    }


    override fun <T> showData(data: T) {
        val value = data as DataModel
        with(bindingView){
            title.text = value.title
            body.text = value.body
        }
    }


    override fun showErrorMessage() {
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