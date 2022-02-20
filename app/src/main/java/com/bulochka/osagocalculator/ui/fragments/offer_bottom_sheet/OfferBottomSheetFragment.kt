package com.bulochka.osagocalculator.ui.fragments.offer_bottom_sheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.toColorInt
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.data.model.Offer
import com.bulochka.osagocalculator.databinding.FragmentOfferBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OfferBottomSheetFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentOfferBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val offer get() = requireArguments().getSerializable(SELECTED_OFFER) as Offer

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOfferBottomSheetBinding.inflate(inflater, container, false)
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED

        showOffer()

        return binding.root
    }

    private fun showOffer() {
        binding.offer.apply {
            offerName.text = offer.name
            offerPrice.text = "${offer.price} ₽"
            offerRating.text = offer.rating.toString()
            offerAvatar.loadUrl(offer.branding.bankLogoUrlSVG)
            avatarCard.setCardBackgroundColor("#${offer.branding.backgroundColor}".toColorInt())
        }
    }

    private fun ImageView.loadUrl(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
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