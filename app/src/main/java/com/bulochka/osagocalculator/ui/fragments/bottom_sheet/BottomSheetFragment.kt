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
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.databinding.FragmentBottomSheetBinding
import com.bulochka.osagocalculator.data.model.Data
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val dataList get() = requireArguments().getSerializable(DATA) as MutableList<Data>
    private val selectedData get() = requireArguments().getInt(SELECTED_DATA)

    private var dataIndex: Int = 0

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        binding.root.minHeight = (Resources.getSystem().displayMetrics.heightPixels)/2
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataIndex = savedInstanceState?.getInt(SELECTED_DATA) ?: selectedData

        initViews()
        setUpClickListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        updateData()
        parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(DATA to dataList))
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

    companion object {
        private const val TAG = "BottomSheetFragment"
        const val REQUEST_KEY = "REQUEST_DATA"
        const val DATA = "data"
        const val SELECTED_DATA = "selected_data"


        fun show(manager: FragmentManager, selectedData: Int, dataList: List<Data>) {
            val dialogFragment = BottomSheetFragment()
            dialogFragment.arguments = bundleOf(
                SELECTED_DATA to selectedData,
                DATA to dataList
            )
            dialogFragment.show(manager, TAG)
        }

        fun setUpListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (List<Data>) -> Unit
        ) {
            manager.setFragmentResultListener(
                REQUEST_KEY,
                lifecycleOwner,
                { _, result ->
                    listener.invoke(result.getSerializable(DATA) as List<Data>)
                })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_DATA, dataIndex)
    }
}
