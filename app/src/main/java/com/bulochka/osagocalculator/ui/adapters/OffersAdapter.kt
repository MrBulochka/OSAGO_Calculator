package com.bulochka.osagocalculator.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bulochka.osagocalculator.data.model.Offer
import com.bulochka.osagocalculator.databinding.ItemOfferBinding
import com.bulochka.osagocalculator.utils.SvgLoader.loadUrl

class OffersAdapter: ListAdapter<Offer, OffersAdapter.OfferViewHolder>(OffersDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = getItem(position)
        holder.onBind(offer)
        holder.itemView.setOnClickListener {
            onOfferClickListener?.invoke(offer)
        }
    }

    class OfferViewHolder(var binding: ItemOfferBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(offer: Offer) {
            binding.apply {
                offerName.text = offer.name
                offerPrice.text = "${offer.price} ₽"
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
    }

    class OffersDiffUtils: DiffUtil.ItemCallback<Offer>(){
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }
    }

    private var onOfferClickListener: ((Offer) -> Unit)? = null
    fun setOnOfferClickListener(listener: (Offer) -> Unit) {
        onOfferClickListener = listener
    }
}