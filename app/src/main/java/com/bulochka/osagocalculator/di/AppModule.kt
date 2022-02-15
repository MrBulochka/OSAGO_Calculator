package com.bulochka.osagocalculator.di

import com.bulochka.osagocalculator.repository.CoefficientsRepository
import com.bulochka.osagocalculator.ui.fragments.coefficients.CoefficientsViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    single { CoefficientsRepository(get(), get(), get()) }
    viewModel { CoefficientsViewModel(get()) }
}