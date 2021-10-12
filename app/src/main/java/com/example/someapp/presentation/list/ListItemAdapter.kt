package com.example.someapp.presentation.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.someapp.databinding.ItemListBinding
import com.example.someapp.domain.DataModel
import kotlin.properties.Delegates

class ListItemAdapter : RecyclerView.Adapter<ListItemAdapter.ItemViewHolder>() {

    var dataList: List<DataModel> by Delegates.observable(emptyList()) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            notifyDataSetChanged()
        }
    }

    var onClickItem: (Long) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemListBinding = ItemListBinding.inflate(inflater, parent, false)
        return ItemViewHolder(itemListBinding, onClickItem)


    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class ItemViewHolder(
        private val binding: ItemListBinding,
        private val onClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: DataModel) {
            with(binding){
                root.setOnClickListener { onClick(data.id) }
                title.text = data.title
                body.text = data.body
            }
        }
    }

}