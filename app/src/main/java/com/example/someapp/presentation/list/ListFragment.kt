package com.example.someapp.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.someapp.R
import com.example.someapp.databinding.FragmentListBinding
import com.example.someapp.presentation.list.placeholder.PlaceholderContent
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _bindingView: FragmentListBinding? = null
    private val bindingView get() = _bindingView!!

    private lateinit var listAdapter: ListItemAdapter

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
        }

//        listAdapter.onClickItem = {
//            val intent = Intent(activity, DetailActivity::class.java)
//            intent.putExtra(GROUP_ID, it)
//            startActivity(intent)
//        }
    }

    private fun setViewModel() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _bindingView = null
    }
}