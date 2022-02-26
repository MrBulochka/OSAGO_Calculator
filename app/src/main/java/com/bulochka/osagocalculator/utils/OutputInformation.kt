package com.bulochka.osagocalculator.utils

import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.bulochka.osagocalculator.data.model.Coefficient

object OutputInformation {

    fun getPriceText(value: String): String {
        var price = ""
        val length = value.length
        for (i in 0 until length) {
            price += value[i]
            if ((length - i - 1) % 3 == 0)
                price += " "
        }
        price += "₽"
        return price
    }

    fun getHeaderText(coefficients: List<Coefficient>, firstColor: Int, secondColor: Int): Spanned {
        var header = ""
        if (coefficients.size > 1) {
            header = getColoredSpanned(
                coefficients[0].headerValue,
                firstColor)
            for (i in 1 until coefficients.size) {
                header += getColoredSpanned("×", secondColor)
                header += getColoredSpanned(
                    coefficients[i].headerValue,
                    firstColor
                )
            }
        }
        return HtmlCompat.fromHtml(header, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun getColoredSpanned(text: String, color: Int): String {
        return "<font color=$color>$text</font>"
    }
}