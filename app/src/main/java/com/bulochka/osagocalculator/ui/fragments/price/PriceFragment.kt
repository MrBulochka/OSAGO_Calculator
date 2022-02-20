package com.bulochka.osagocalculator.ui.fragments.price

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.Offer
import com.bulochka.osagocalculator.data.model.SendCoefficients
import com.bulochka.osagocalculator.databinding.FragmentPriceBinding
import com.bulochka.osagocalculator.ui.adapters.CoefficientsAdapter
import com.bulochka.osagocalculator.ui.adapters.OffersAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PriceFragment : Fragment() {

    private var _binding: FragmentPriceBinding? = null
    private val binding get() = _binding!!

    private val priceViewModel: PriceViewModel by viewModel()

    private lateinit var coefficientsAdapter: CoefficientsAdapter
    private lateinit var offersAdapter: OffersAdapter
    private var coefficients = listOf<Coefficient>()
    private val args: PriceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceBinding.inflate(inflater, container, false)

        coefficients = args.coefficients.factors

        loadOffers(coefficients)
        initRecyclers()
        setUpListeners()
        setUpObservers()

        return binding.root
    }

    private fun loadOffers(coefficients: List<Coefficient>) {
        priceViewModel.postCoefficients(SendCoefficients(coefficients))
    }

    private fun initRecyclers() {
        binding.coefficients.coefficientRecycler.apply {
            coefficientsAdapter = CoefficientsAdapter()
            adapter = coefficientsAdapter
            coefficientsAdapter.submitList(coefficients)
            updateHeader(coefficients)
        }

        binding.offersRecycler.apply {
            offersAdapter = OffersAdapter()
            adapter = offersAdapter
        }
    }

    private fun setUpListeners() {
        binding.coefficients.expandBtn.setOnClickListener {
            val isOpen = binding.coefficients.coefficientRecycler.visibility == View.VISIBLE
            priceViewModel.setCoefficientsState(!isOpen)
        }

        offersAdapter.setOnOfferClickListener { offer ->
            moveToOffer(offer)
        }
    }

    private fun setUpObservers() {
        priceViewModel.coefficientsVisibility.observe(viewLifecycleOwner) {
            setCoefficientsVisibility(it)
        }

        priceViewModel.offersResponse.observe(viewLifecycleOwner) {
            offersAdapter.submitList(it.offers)
            updateUI(it.actionText)
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

    private fun updateHeader(coefficients: List<Coefficient>) {
        if (coefficients.size > 1) {
            var header = coefficients[0].headerValue
            for (i in 1 until coefficients.size) {
                header += "×${coefficients[i].headerValue}"
            }
            binding.coefficients.header.text = header
        }
    }

    private fun updateUI(buttonText: String) {
        binding.apply {
            shimmer.stopShimmer()
            shimmer.visibility = GONE
            offersRecycler.visibility = VISIBLE
            calculateBtn.setBackgroundResource(R.drawable.button_active)
            calculateBtn.setTextColor(resources.getColor(R.color.white))
            calculateBtn.text = buttonText
            calculateBtn.isClickable = true
        }
    }

    private fun moveToOffer(offer: Offer) {
        val bundle = Bundle()
        bundle.putSerializable("offer", offer)
        findNavController().navigate(R.id.action_price_to_coefficients, bundle)
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