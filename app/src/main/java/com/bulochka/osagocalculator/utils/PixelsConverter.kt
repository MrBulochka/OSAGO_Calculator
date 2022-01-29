package com.bulochka.osagocalculator.utils

import android.content.Context

object PixelsConverter {
    fun convertDpToPx(context: Context, dp: Float): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}