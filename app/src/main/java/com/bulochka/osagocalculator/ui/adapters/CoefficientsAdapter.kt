package com.bulochka.osagocalculator.ui.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bulochka.osagocalculator.databinding.ItemCoefficientBinding
import com.bulochka.osagocalculator.data.model.Coefficient

class CoefficientsAdapter: ListAdapter<Coefficient, CoefficientsAdapter.CoefficientsViewHolder>(CoefficientsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoefficientsViewHolder {
        val binding = ItemCoefficientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoefficientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoefficientsViewHolder, position: Int) {
        val coefficients = getItem(position)
        holder.onBind(coefficients)
    }

    inner class CoefficientsViewHolder(var binding: ItemCoefficientBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(coefficient: Coefficient) {
            binding.apply {
                textTitle.text = coefficient.title
                textValue.text = coefficient.value
                textName.text = "(${coefficient.name})"
                textDetail.text = coefficient.detailText
            }
        }
    }

    class CoefficientsDiffUtil: DiffUtil.ItemCallback<Coefficient>() {
        override fun areItemsTheSame(oldItem: Coefficient, newItem: Coefficient): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Coefficient, newItem: Coefficient): Boolean {
            return oldItem == newItem
        }
    }
}

class CoefficientsItemDecoration(private val spacing: Int):
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = spacing
        outRect.right = spacing
        outRect.top = spacing
    }
}