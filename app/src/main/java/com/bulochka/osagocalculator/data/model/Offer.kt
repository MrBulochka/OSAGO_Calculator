package com.bulochka.osagocalculator.data.model

data class Offer(
    val name: String,
    val rating: Float,
    val price: Int,
    val branding: Brand
)

data class Brand(
    val backgroundColor: String,
    val iconTitle: String,
    val bankLogoUrlPDF: String,
    val bankLogoUrlSVG: String
)