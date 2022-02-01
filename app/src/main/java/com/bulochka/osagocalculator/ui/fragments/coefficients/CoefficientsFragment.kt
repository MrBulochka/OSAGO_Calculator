package com.bulochka.osagocalculator.ui.fragments.coefficients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bulochka.osagocalculator.AppApplication
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.databinding.FragmentCoefficientsBinding
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.ui.adapters.CoefficientsAdapter
import com.bulochka.osagocalculator.ui.adapters.CoefficientsItemDecoration
import com.bulochka.osagocalculator.ui.adapters.DataAdapter
import com.bulochka.osagocalculator.ui.adapters.DataItemDecoration
import com.bulochka.osagocalculator.ui.fragments.bottom_sheet.BottomSheetFragment
import com.bulochka.osagocalculator.utils.PixelsConverter
import java.io.Serializable

class CoefficientsFragment: Fragment(R.layout.fragment_coefficients) {

    private val coefficientsViewModel: CoefficientsViewModel by viewModels {
        CoefficientsViewModelFactory((requireActivity().application as AppApplication).repository)
    }

    private lateinit var binding: FragmentCoefficientsBinding
    private lateinit var coefficientsAdapter: CoefficientsAdapter
    private lateinit var dataAdapter: DataAdapter
    private var dataList = listOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoefficientsBinding.inflate(inflater, container, false)

        initRecyclers()
        setUpObservers()
        setUpClickListeners()

        return binding.root
    }

    private fun initRecyclers() {
        binding.coefficientRecycler.apply {
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
        binding.apply {
            expandBtn.setOnClickListener {
                if (coefficientRecycler.visibility == GONE) {
                    coefficientRecycler.visibility = VISIBLE
                    expandBtn.setBackgroundResource(R.drawable.ic_hide)
                } else {
                    coefficientRecycler.visibility = GONE
                    expandBtn.setBackgroundResource(R.drawable.ic_open)
                }
            }
        }

        dataAdapter.setOnDataClickListener { position ->
            val bundle = Bundle()
            bundle.putSerializable("data", dataList as Serializable)
            bundle.putInt("selected_data", position)
//            bundle.putInt("selected_data", data.id!! - 1)

            val dialogFragment = BottomSheetFragment()
            dialogFragment.arguments = bundle
            dialogFragment.show(requireActivity().supportFragmentManager, BottomSheetFragment.TAG)
        }
    }

    private fun setUpObservers() {
        coefficientsViewModel.coefficients.observe(viewLifecycleOwner) { coefficients ->
            coefficientsAdapter.submitList(coefficients)
            updateHeader(coefficients)
        }

        coefficientsViewModel.data.observe(viewLifecycleOwner) { data ->
            dataList = data
            dataAdapter.submitList(data)
            Log.d("CoefficientsFragment", "$data")
        }
    }

    private fun updateHeader(coefficients: List<Coefficient>) {
        if (coefficients.size > 1) {
            var header = coefficients[0].headerValue
            for (i in 1 until coefficients.size) {
                header += "×${coefficients[i].headerValue}"
            }
            binding.header.text = header
        }
    }
}