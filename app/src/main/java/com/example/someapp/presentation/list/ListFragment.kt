package com.example.someapp.presentation.list

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.someapp.R
import com.example.someapp.databinding.FragmentListBinding
import com.example.someapp.domain.DataModel
import com.example.someapp.presentation.ViewState
import dagger.hilt.android.AndroidEntryPoint


/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _bindingView: FragmentListBinding? = null
    private val bindingView get() = _bindingView!!

    private lateinit var listAdapter: ListItemAdapter

    private val viewModel: ListViewModel by viewModels()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingView = FragmentListBinding.inflate(layoutInflater)
        return bindingView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setViewModel()
    }

    private fun setUpUI() {
        progressDialog = ProgressDialog(context)
        listAdapter = ListItemAdapter()
        bindingView.list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = listAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }

        listAdapter.onClickItem = {
            val action = ListFragmentDirections.actionNavListToNavDetail(id = it)
            findNavController().navigate(action)
        }
    }

    private fun setViewModel() {
        viewModel.viewState.observe(::getLifecycle, ::updateUI)
        viewModel.getDataList()
    }

    private fun updateUI(viewState: ViewState<List<DataModel>>) {
        when (viewState) {
            is ViewState.ShowData -> showData(viewState.data)
            ViewState.Error -> showErrorMessage()
            ViewState.Loading -> showLoadingDialogFragment()
        }
    }


    private fun showData(data: List<DataModel>) {
        progressDialog.dismiss()
        listAdapter.dataList = data
    }

    private fun showLoadingDialogFragment() {
        progressDialog.setMessage(getString(R.string.downloading_title_dialog))
        progressDialog.show()
    }

    private fun showErrorMessage() {
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindingView = null
    }
}