package com.bulochka.osagocalculator.ui.fragments.coefficients.bottom_sheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.databinding.FragmentBottomSheetBinding
import com.bulochka.osagocalculator.data.model.Data
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BottomSheetFragment"
    }

    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var dataList: List<Data>
    private var selectedData: Int = 0

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        dataList = arguments?.getSerializable("data") as List<Data>

        initViews()
        setVisibility()
        setUpClickListeners()

        return binding.root
    }

    private fun initViews() {
        binding.apply {
            inputData.showKeyboard()
            selectedData = arguments?.getInt("selected_data")!!
            updateData()
        }
    }

    private fun setUpClickListeners() {
        binding.nextBtn.setOnClickListener {
            selectedData += 1
            updateData()
            setVisibility()
        }

        binding.backBtn.setOnClickListener {
            selectedData -= 1
            updateData()
            setVisibility()
        }
    }

    private fun updateData() {
        binding.apply {
            title.text = dataList[selectedData].hint
            inputData.setText(dataList[selectedData].value)

        }
    }

    private fun setVisibility() {
        when (selectedData) {
            0 -> {// 0 - первый элемент
                binding.nextBtn.visibility = VISIBLE
                binding.backBtn.visibility = INVISIBLE
            }
            5 -> {// 5 - последний элемент
                binding.nextBtn.visibility = INVISIBLE
                binding.backBtn.visibility = VISIBLE
            }
            else -> {
                binding.nextBtn.visibility = VISIBLE
                binding.backBtn.visibility = VISIBLE
            }
        }
    }

    private fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}
