package com.bulochka.osagocalculator.utils

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

object SvgLoader {

    fun ImageView.loadUrl(url: String?) {
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