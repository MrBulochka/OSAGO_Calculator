package com.bulochka.osagocalculator.repository

import com.bulochka.osagocalculator.data.local.dao.CoefficientDao
import com.bulochka.osagocalculator.data.local.dao.DataDao
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.Data
import kotlinx.coroutines.flow.Flow

class Repository(private val coefficientDao: CoefficientDao,
                 private val dataDao: DataDao) {

    val coefficients: Flow<List<Coefficient>> = coefficientDao.getCoefficients()
    val data: Flow<List<Data>> = dataDao.getData()

    suspend fun getDataById(id: Int): Data {
        return dataDao.getDataById(id)
    }
}