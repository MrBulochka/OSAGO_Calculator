package com.bulochka.osagocalculator.data.remote

import com.bulochka.osagocalculator.data.model.CoefficientsResponse
import com.bulochka.osagocalculator.data.model.SendData
import retrofit2.http.Body
import retrofit2.http.POST

interface OsagoApiService {

    @POST("rationDetail")
    suspend fun postData(@Body data: SendData): CoefficientsResponse
}