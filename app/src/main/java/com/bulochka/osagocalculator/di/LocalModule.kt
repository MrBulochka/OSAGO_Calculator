package com.bulochka.osagocalculator.di

import com.bulochka.osagocalculator.data.local.AppDatabase
import com.bulochka.osagocalculator.repository.CoefficientsRepository
import com.bulochka.osagocalculator.ui.fragments.bottom_sheet.BottomSheetViewModel
import com.bulochka.osagocalculator.ui.fragments.coefficients.CoefficientsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationScope = CoroutineScope(SupervisorJob())

val localModule = module {

    single { AppDatabase.getDatabase(androidApplication(), applicationScope).coefficientDao() }
    single { AppDatabase.getDatabase(androidApplication(), applicationScope).dataDao() }
}
