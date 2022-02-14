package com.bulochka.osagocalculator.ui.fragments.coefficients

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.databinding.FragmentCoefficientsBinding
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.data.model.SendData
import com.bulochka.osagocalculator.ui.adapters.CoefficientsAdapter
import com.bulochka.osagocalculator.ui.adapters.CoefficientsItemDecoration
import com.bulochka.osagocalculator.ui.adapters.DataAdapter
import com.bulochka.osagocalculator.ui.adapters.DataItemDecoration
import com.bulochka.osagocalculator.ui.fragments.bottom_sheet.BottomSheetFragment
import com.bulochka.osagocalculator.utils.PixelsConverter
import java.io.Serializable

class CoefficientsFragment: Fragment() {

    private val coefficientsViewModel: CoefficientsViewModel by viewModel()

    private var _binding: FragmentCoefficientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var coefficientsAdapter: CoefficientsAdapter
    private lateinit var dataAdapter: DataAdapter
    private var dataList = listOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoefficientsBinding.inflate(inflater, container, false)

        initRecyclers()
        setUpObservers()
        setUpClickListeners()

        return binding.root
    }

    private fun initRecyclers() {
        binding.coefficients.coefficientRecycler.apply {
            coefficientsAdapter = CoefficientsAdapter()
            adapter = coefficientsAdapter
            val spacing = PixelsConverter.convertDpToPx(context, 16f)
            addItemDecoration(CoefficientsItemDecoration(spacing))
        }

        binding.dataRecycler.apply {
            dataAdapter = DataAdapter()
            adapter = dataAdapter
            val spacing = PixelsConverter.convertDpToPx(context, 12f)
            addItemDecoration(DataItemDecoration(spacing))
        }
    }

    private fun setUpClickListeners() {
        binding.coefficients.expandBtn.setOnClickListener {
            val isOpen = binding.coefficients.coefficientRecycler.visibility == VISIBLE
            coefficientsViewModel.setCoefficientsState(!isOpen)
        }

        dataAdapter.setOnDataClickListener { position ->
            showBottomSheet(position)
        }

        binding.calculateBtn.setOnClickListener {
            findNavController().navigate(R.id.action_coefficients_to_price)
        }
    }

    private fun setUpObservers() {
        coefficientsViewModel.coefficients.observe(viewLifecycleOwner) { coefficients ->
            coefficientsAdapter.submitList(coefficients)
            updateHeader(coefficients)
            binding.progressBar.visibility = INVISIBLE
            binding.calculateBtn.setText(R.string.calculate_OSAGO)
            updateCalculateButton()
        }

        coefficientsViewModel.data.observe(viewLifecycleOwner) { data ->
            dataList = data
            dataAdapter.submitList(data.toList())
        }

        coefficientsViewModel.coefficientsVisibility.observe(viewLifecycleOwner) {
            setCoefficientsVisibility(it)
        }
    }

    private fun updateHeader(coefficients: List<Coefficient>) {
        if (coefficients.size > 1) {
            var header = coefficients[0].headerValue
            for (i in 1 until coefficients.size) {
                header += "×${coefficients[i].headerValue}"
            }
            binding.coefficients.header.text = header
        }
    }

    private fun setCoefficientsVisibility(isOpen: Boolean) {
        binding.coefficients.apply {
            if (isOpen) {
                coefficientRecycler.visibility = VISIBLE
                expandBtn.setBackgroundResource(R.drawable.ic_hide)
            } else {
                coefficientRecycler.visibility = GONE
                expandBtn.setBackgroundResource(R.drawable.ic_open)
            }
        }
    }

    private fun showBottomSheet(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(BottomSheetFragment.DATA, dataList as Serializable)
        bundle.putInt(BottomSheetFragment.SELECTED_DATA, position)

        val dialogFragment = BottomSheetFragment()
        dialogFragment.arguments = bundle
        dialogFragment.show(childFragmentManager, BottomSheetFragment.TAG)

        setDismissListener(dialogFragment)
        // не обрабатывается событие при вызове "dismiss" (клик на кнопку "продолжить")
        // в BottomSheetFragment
    }

    private fun setDismissListener(dialogFragment: BottomSheetFragment) {
        childFragmentManager.executePendingTransactions()
        dialogFragment.dialog?.setOnDismissListener {
            coefficientsViewModel.postData(SendData(dataList))
            binding.calculateBtn.text = ""
            binding.calculateBtn.setBackgroundResource(R.drawable.button_inactive)
            binding.progressBar.visibility = VISIBLE
            dialogFragment.dismiss()
        }
    }

    private fun updateCalculateButton() {
        var isActive = true
        for (data in dataList)
            if (data.value.isEmpty())
                isActive = false
        binding.calculateBtn.apply {
            if (isActive) {
                setBackgroundResource(R.drawable.button_active)
                isClickable = true
                setTextColor(resources.getColor(R.color.white))
            } else {
                setBackgroundResource(R.drawable.button_inactive)
                isClickable = false
                setTextColor(resources.getColor(R.color.main_20))
            }
        }
    }
}