package com.bulochka.osagocalculator.ui.fragments.bottom_sheet

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.bulochka.osagocalculator.AppApplication
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.databinding.FragmentBottomSheetBinding
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.ui.fragments.coefficients.CoefficientsViewModel
import com.bulochka.osagocalculator.ui.fragments.coefficients.CoefficientsViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BottomSheetFragment"
    }

    private val bottomSheetViewModel: BottomSheetViewModel by viewModels {
        BottomSheetViewModelFactory((requireActivity().application as AppApplication).repository)
    }
    private lateinit var binding: FragmentBottomSheetBinding
    private var dataList = listOf<Data>()
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
        setUpClickListeners()

        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        updateData()
        bottomSheetViewModel.updateAllData(dataList)
    }

    private fun initViews() {
        binding.apply {
            inputData.showKeyboard()
            selectedData = arguments?.getInt("selected_data")!!
            updateView()
        }
    }

    private fun setUpClickListeners() {
        binding.nextBtn.setOnClickListener {
            updateData()
            selectedData += 1
            updateView()
        }

        binding.backBtn.setOnClickListener {
            updateData()
            selectedData -= 1
            updateView()
        }

        binding.confirmBtn.setOnClickListener {
            updateData()
            dismiss()
        }
    }

    private fun updateView() {
        binding.apply {
            title.text = dataList[selectedData].hint
            inputData.setText(dataList[selectedData].value)
            setVisibility()
        }
    }

    private fun setVisibility() {
        binding.apply {
            when (selectedData) {
                0 -> {
                    nextBtn.visibility = VISIBLE
                    confirmBtn.visibility = INVISIBLE
                    backBtn.visibility = INVISIBLE
                }
                dataList.lastIndex -> {
                    nextBtn.visibility = INVISIBLE
                    confirmBtn.visibility = VISIBLE
                    backBtn.visibility = VISIBLE
                }
                else -> {
                    nextBtn.visibility = VISIBLE
                    confirmBtn.visibility = INVISIBLE
                    backBtn.visibility = VISIBLE
                }
            }
        }
    }

    private fun updateData() {
        dataList[selectedData].value = binding.inputData.text.toString()
    }

    private fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}
