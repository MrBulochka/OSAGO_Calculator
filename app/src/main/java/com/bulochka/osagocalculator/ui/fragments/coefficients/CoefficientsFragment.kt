package com.bulochka.osagocalculator.ui.fragments.coefficients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.data.model.*
import com.bulochka.osagocalculator.databinding.FragmentCoefficientsBinding
import com.bulochka.osagocalculator.ui.adapters.CoefficientsAdapter
import com.bulochka.osagocalculator.ui.adapters.DataAdapter
import com.bulochka.osagocalculator.ui.fragments.input_bottom_sheet.InputBottomSheetFragment
import com.bulochka.osagocalculator.ui.fragments.offer_bottom_sheet.OfferBottomSheetFragment

class CoefficientsFragment: Fragment() {

    private val coefficientsViewModel: CoefficientsViewModel by viewModel()

    private var _binding: FragmentCoefficientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var coefficientsAdapter: CoefficientsAdapter
    private lateinit var dataAdapter: DataAdapter
    private var dataList = listOf<Data>()
    private var coefficients = listOf<Coefficient>()

    private var isOfferOpen = false
    private val args: CoefficientsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoefficientsBinding.inflate(inflater, container, false)

        initRecyclers()
        setUpObservers()
        setUpListeners()

        isOfferOpen = savedInstanceState?.getBoolean(IS_OFFER_OPEN) ?: false
        showOfferBottomSheet()

        return binding.root
    }

    private fun initRecyclers() {
        binding.coefficients.coefficientRecycler.apply {
            coefficientsAdapter = CoefficientsAdapter()
            adapter = coefficientsAdapter
        }

        binding.dataRecycler.apply {
            dataAdapter = DataAdapter()
            adapter = dataAdapter
        }
    }

    private fun setUpListeners() {
        setUpInputBottomSheetListener()

        binding.coefficients.expandBtn.setOnClickListener {
            val isOpen = binding.coefficients.coefficientRecycler.visibility == VISIBLE
            coefficientsViewModel.setCoefficientsState(!isOpen)
        }

        dataAdapter.setOnDataClickListener { position ->
            showInputBottomSheet(position)
        }

        binding.calculateBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("coefficients", CoefficientsResponse(coefficients))
            findNavController().navigate(R.id.action_coefficients_to_price, bundle)
        }
    }

    private fun setUpObservers() {
        coefficientsViewModel.data.observe(viewLifecycleOwner) { data ->
            dataList = data
            dataAdapter.submitList(data.toList())
        }

        coefficientsViewModel.coefficients.observe(viewLifecycleOwner) {
            coefficients = it
            coefficientsAdapter.submitList(coefficients)
            updateHeader(coefficients)
            binding.progressBar.visibility = INVISIBLE
            binding.calculateBtn.setText(R.string.calculate_OSAGO)
            updateCalculateButton()
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
                expandBtn.setImageResource(R.drawable.ic_hide)
            } else {
                coefficientRecycler.visibility = GONE
                expandBtn.setImageResource(R.drawable.ic_open)
            }
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
                setTextColor(requireActivity().getColor(R.color.white))
            } else {
                setBackgroundResource(R.drawable.button_inactive)
                isClickable = false
                setTextColor(requireActivity().getColor(R.color.main_20))
            }
        }
    }

    private fun showInputBottomSheet(position: Int) {
        InputBottomSheetFragment.show(childFragmentManager, position, dataList)
    }

    private fun showOfferBottomSheet() {
        val offer = args.offer
        if (offer != null && !isOfferOpen) {
            OfferBottomSheetFragment.show(childFragmentManager, offer)
            isOfferOpen = true
        }
    }

    private fun setUpInputBottomSheetListener() {
        InputBottomSheetFragment.setUpListener(childFragmentManager, this) {
            coefficientsViewModel.updateAllData(it)
            coefficientsViewModel.postData(SendData(it))
            binding.apply {
                calculateBtn.text = ""
                calculateBtn.setBackgroundResource(R.drawable.button_inactive)
                calculateBtn.isClickable = false
                progressBar.visibility = VISIBLE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_OFFER_OPEN, isOfferOpen)
    }

    companion object {
        const val IS_OFFER_OPEN = "IS_OFFER_OPEN"
    }
}