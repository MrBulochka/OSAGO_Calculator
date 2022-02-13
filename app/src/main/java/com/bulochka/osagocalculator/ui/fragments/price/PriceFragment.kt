package com.bulochka.osagocalculator.ui.fragments.price

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bulochka.osagocalculator.databinding.FragmentPriceBinding

class PriceFragment : Fragment() {

    private var _binding: FragmentPriceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.shimmer.startShimmer()
    }

    override fun onPause() {
        binding.shimmer.stopShimmer()
        super.onPause()
    }
}