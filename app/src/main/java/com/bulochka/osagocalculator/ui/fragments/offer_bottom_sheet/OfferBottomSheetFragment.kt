package com.bulochka.osagocalculator.ui.fragments.offer_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.data.model.Offer
import com.bulochka.osagocalculator.databinding.FragmentOfferBottomSheetBinding
import com.bulochka.osagocalculator.utils.OutputInformation
import com.bulochka.osagocalculator.utils.SvgLoader.loadUrl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OfferBottomSheetFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentOfferBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var offer: Offer

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOfferBottomSheetBinding.inflate(inflater, container, false)
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        offer = requireArguments().getSerializable(SELECTED_OFFER) as Offer

        showOffer()
        setUpListeners()

        return binding.root
    }

    private fun showOffer() {
        binding.offer.apply {
            offerName.text = offer.name
            offerPrice.text = OutputInformation.getPriceText(offer.price.toString())
            offerRating.text = offer.rating.toString()
            if (offer.branding.bankLogoUrlSVG != null) {
                offerAvatar.loadUrl(offer.branding.bankLogoUrlSVG)
            } else {
                offerTitle.text = offer.branding.iconTitle
                offerTitle.setTextColor("#${offer.branding.fontColor}".toColorInt())
                avatarCard.setCardBackgroundColor("#${offer.branding.backgroundColor}".toColorInt())
            }
        }
    }

    private fun setUpListeners() {
        binding.readyBtn.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        private const val TAG = "OfferBottomSheetFragment"
        const val SELECTED_OFFER = "SELECTED_OFFER"

        fun show(manager: FragmentManager, offer: Offer) {
            val dialogFragment = OfferBottomSheetFragment()
            dialogFragment.arguments = bundleOf(
                SELECTED_OFFER to offer
            )
            dialogFragment.show(manager, TAG)
        }
    }
}