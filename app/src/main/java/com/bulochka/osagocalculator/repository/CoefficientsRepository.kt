package com.bulochka.osagocalculator.repository

import com.bulochka.osagocalculator.data.local.dao.CoefficientDao
import com.bulochka.osagocalculator.data.local.dao.DataDao
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.CoefficientsResponse
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.data.model.SendData
import com.bulochka.osagocalculator.data.remote.OsagoApiService
import kotlinx.coroutines.flow.Flow

class CoefficientsRepository(private val coefficientDao: CoefficientDao,
                             private val dataDao: DataDao,
                             private val osagoApiService: OsagoApiService) {

    val coefficients: Flow<List<Coefficient>> = coefficientDao.getCoefficients()
    val data: Flow<List<Data>> = dataDao.getData()

    suspend fun updateAllData(listData: List<Data>) {
        dataDao.updateAllData(listData)
    }

    suspend fun updateAllCoefficients(coefficients: List<Coefficient>) {
        coefficientDao.updateAllCoefficients(coefficients)
    }

    suspend fun postData(data: SendData): CoefficientsResponse {
        return osagoApiService.postData(data)
    }
}