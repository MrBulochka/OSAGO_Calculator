package com.bulochka.osagocalculator.ui.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
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
                it(data)
            }
        }
    }

    class DataViewHolder(var binding: ItemDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Data) {
            binding.apply {
                hint.text = data.hint
                value.text = data.value
            }

        }
    }

    class DataDiffUtil: DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.hint == newItem.hint
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    private var onDataClickListener: ((Data) -> Unit)? = null
    fun setOnDataClickListener(listener: (Data) -> Unit) {
        onDataClickListener = listener
    }
}

class DataItemDecoration(private val spacing: Int):
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position > 0)
            outRect.top = spacing
    }
}