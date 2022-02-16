package com.bulochka.osagocalculator.data.model

import java.io.Serializable

data class CoefficientsResponse (
    val factors: List<Coefficient>
): Serializable