package com.bulochka.osagocalculator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "data_table")
data class Data (

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val hint: String,

    val value: String
): Serializable