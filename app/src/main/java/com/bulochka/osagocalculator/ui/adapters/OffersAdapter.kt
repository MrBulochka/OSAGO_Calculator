package com.bulochka.osagocalculator.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bulochka.osagocalculator.data.model.Offer
import com.bulochka.osagocalculator.databinding.ItemOfferBinding

class OffersAdapter: ListAdapter<Offer, OffersAdapter.OfferViewHolder>(OffersDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = getItem(position)
        holder.onBind(offer)
        holder.itemView.setOnClickListener {
            onOfferClickListener?.let {
                it(offer)
            }
        }
    }

    class OfferViewHolder(var binding: ItemOfferBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(offer: Offer) {
            binding.apply {
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