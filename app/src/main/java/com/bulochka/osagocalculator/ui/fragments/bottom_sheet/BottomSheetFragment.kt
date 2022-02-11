package com.bulochka.osagocalculator.ui.fragments.bottom_sheet

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.databinding.FragmentBottomSheetBinding
import com.bulochka.osagocalculator.data.model.Data
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetFragment: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BottomSheetFragment"
        const val DATA = "data"
        const val SELECTED_DATA = "selected_data"
    }

    private val bottomSheetViewModel: BottomSheetViewModel by viewModel()
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var dataList = mutableListOf<Data>()
    private var dataIndex: Int = 0

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        // Шторка расширяется только с minHeight, других способов я не нашел
        // по идее тут надо еще считать высоту клавиатуры, чтобы размер шторки был как в макете
        binding.root.minHeight = (Resources.getSystem().displayMetrics.heightPixels)/2
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataList = arguments?.getSerializable(DATA) as MutableList<Data>
        dataIndex = requireArguments().getInt(SELECTED_DATA)

        initViews()
        setUpClickListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        updateData()
        bottomSheetViewModel.updateAllData(dataList)
    }

    private fun initViews() {
        binding.apply {
            updateView()
            inputData.showKeyboard()
        }
    }

    private fun setUpClickListeners() {
        binding.nextBtn.setOnClickListener {
            updateData()
            dataIndex += 1
            updateView()
        }

        binding.backBtn.setOnClickListener {
            updateData()
            dataIndex -= 1
            updateView()
        }

        binding.confirmBtn.setOnClickListener {
            updateData()
            dismiss()
        }

        binding.crossBtn.setOnClickListener {
            binding.inputData.setText("")
        }
    }

    private fun updateView() {
        binding.apply {
            if (dataList.size >= dataIndex) {
                title.text = dataList[dataIndex].hint
                inputData.setText(dataList[dataIndex].value)
                inputData.inputType = dataList[dataIndex].inputType
            }
            setVisibility()
        }
    }

    private fun setVisibility() {
        binding.apply {
            when (dataIndex) {
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
        val id = dataList[dataIndex].id
        val hint = dataList[dataIndex].hint
        val value = binding.inputData.text.toString()
        dataList[dataIndex] = Data(id, hint, value, binding.inputData.inputType)
    }

    private fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}
