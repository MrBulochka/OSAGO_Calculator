package com.bulochka.osagocalculator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "coefficients_table")
data class Coefficient (

        @PrimaryKey
        val title: String,

        var headerValue: String,

        var value: String,

        val name: String,

        val detailText: String
): Serializable
