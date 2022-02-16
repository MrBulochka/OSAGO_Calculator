package com.bulochka.osagocalculator.data.remote

import com.bulochka.osagocalculator.data.model.CoefficientsResponse
import com.bulochka.osagocalculator.data.model.OffersResponse
import com.bulochka.osagocalculator.data.model.SendCoefficients
import com.bulochka.osagocalculator.data.model.SendData
import retrofit2.http.Body
import retrofit2.http.POST

interface OsagoApiService {

    @POST("rationDetail")
    suspend fun postData(@Body data: SendData): CoefficientsResponse

    @POST("startCalculation")
    suspend fun postCoefficients(@Body coefficients: SendCoefficients): OffersResponse
}