package com.bulochka.osagocalculator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bulochka.osagocalculator.data.model.Coefficient
import kotlinx.coroutines.flow.Flow

@Dao
interface CoefficientDao {
    @Query("SELECT * FROM coefficients_table")
    fun getCoefficients(): Flow<List<Coefficient>>

    @Insert
    suspend fun insertCoefficients(coefficients: List<Coefficient>)

    @Update
    suspend fun updateAllCoefficients(coefficient: List<Coefficient>)
}