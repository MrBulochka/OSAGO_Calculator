package com.bulochka.osagocalculator.di

import com.bulochka.osagocalculator.BuildConfig
import com.bulochka.osagocalculator.data.remote.OsagoApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    factory { provideOkHttpClient() }
    factory { provideCurrencyService(get()) }
    single { provideRetrofit(get()) }
}

fun provideOkHttpClient(
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .build()
}

fun provideRetrofit(
    okHttpClient: OkHttpClient
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideCurrencyService(retrofit: Retrofit): OsagoApiService =
    retrofit.create(OsagoApiService::class.java)
