package com.bulochka.osagocalculator.ui.adapters

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bulochka.osagocalculator.databinding.ItemDataBinding
import com.bulochka.osagocalculator.data.model.Data

class DataAdapter: ListAdapter<Data, DataAdapter.DataViewHolder>(DataDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data)
        holder.itemView.setOnClickListener {
            onDataClickListener?.let {
                it(position)
            }
        }
    }

    class DataViewHolder(var binding: ItemDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Data) {
            binding.apply {
                hint.text = data.hint
                value.text = data.value
                if (data.value.isNotEmpty()) {
                    hint.textSize = 14f
                    value.visibility = VISIBLE
                } else {
                    value.visibility = GONE
                    hint.textSize = 16f
                }
            }
        }
    }

    class DataDiffUtil: DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    private var onDataClickListener: ((Int) -> Unit)? = null
    fun setOnDataClickListener(listener: (Int) -> Unit) {
        onDataClickListener = listener
    }
}