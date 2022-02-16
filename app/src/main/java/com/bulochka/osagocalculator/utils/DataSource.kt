package com.bulochka.osagocalculator.utils

import android.content.Context
import com.bulochka.osagocalculator.R
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.Data

class DataSource(private val context: Context) {

    val COEFFICIENTS = listOf(
        Coefficient(
            getStringArray(R.array.coefficient_BT)[0],
            getStringArray(R.array.coefficient_BT)[1],
            getStringArray(R.array.coefficient_BT)[2],
            getStringArray(R.array.coefficient_BT)[3],
            getStringArray(R.array.coefficient_BT)[4],
        ),
        Coefficient(
            getStringArray(R.array.coefficient_KM)[0],
            getStringArray(R.array.coefficient_KM)[1],
            getStringArray(R.array.coefficient_KM)[2],
            getStringArray(R.array.coefficient_KM)[3],
            getStringArray(R.array.coefficient_KM)[4],
        ),
        Coefficient(
            getStringArray(R.array.coefficient_KT)[0],
            getStringArray(R.array.coefficient_KT)[1],
            getStringArray(R.array.coefficient_KT)[2],
            getStringArray(R.array.coefficient_KT)[3],
            getStringArray(R.array.coefficient_KT)[4],
        ),
        Coefficient(
            getStringArray(R.array.coefficient_KBM)[0],
            getStringArray(R.array.coefficient_KBM)[1],
            getStringArray(R.array.coefficient_KBM)[2],
            getStringArray(R.array.coefficient_KBM)[3],
            getStringArray(R.array.coefficient_KBM)[4],
        ),
        Coefficient(
            getStringArray(R.array.coefficient_KO)[0],
            getStringArray(R.array.coefficient_KO)[1],
            getStringArray(R.array.coefficient_KO)[2],
            getStringArray(R.array.coefficient_KO)[3],
            getStringArray(R.array.coefficient_KO)[4],
        ),
        Coefficient(
            getStringArray(R.array.coefficient_KVS)[0],
            getStringArray(R.array.coefficient_KVS)[1],
            getStringArray(R.array.coefficient_KVS)[2],
            getStringArray(R.array.coefficient_KVS)[3],
            getStringArray(R.array.coefficient_KVS)[4],
        ),
    )

    val DATA = listOf(
        Data(null, getString(R.string.city_of_registration), "", 1),
        Data(null, getString(R.string.car_power), "", 3),
        Data(null, getString(R.string.how_many_drivers), "", 3),
        Data(null, getString(R.string.age_of_youngest_driver), "", 1),
        Data(null, getString(R.string.minimum_driving_experience), "", 1),
        Data(null, getString(R.string.how_years_no_accidents), "", 3),
    )

    private fun getString(id: Int) = context.resources.getString(id)
    private fun getStringArray(id: Int) = context.resources.getStringArray(id)
}