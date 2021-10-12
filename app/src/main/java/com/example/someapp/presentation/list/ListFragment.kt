package com.example.someapp.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.someapp.databinding.FragmentListBinding
import com.example.someapp.domain.DataModel
import com.example.someapp.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ListFragment : BaseFragment() {

    private var _bindingView: FragmentListBinding? = null
    private val bindingView get() = _bindingView!!

    private lateinit var listAdapter: ListItemAdapter

    private val viewModel: ListViewModel by viewModels()


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



    override fun <T> showData(data: T){
        listAdapter.dataList = data as List<DataModel>
    }


    override fun showErrorMessage() {
        with(bindingView){
            list.visibility = GONE
            error.visibility = VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindingView = null
    }
}