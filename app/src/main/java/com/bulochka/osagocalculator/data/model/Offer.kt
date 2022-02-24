package com.bulochka.osagocalculator.data.model

import java.io.Serializable

data class Offer(
    val name: String,
    val rating: Float,
    val price: Int,
    val branding: Brand
): Serializable

data class Brand(
    val fontColor: String,
    val backgroundColor: String,
    val iconTitle: String,
    val bankLogoUrlPDF: String,
    val bankLogoUrlSVG: String
): Serializable