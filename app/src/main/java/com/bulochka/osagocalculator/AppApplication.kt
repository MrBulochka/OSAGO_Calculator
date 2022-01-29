package com.bulochka.osagocalculator

import android.app.Application
import com.bulochka.osagocalculator.data.local.AppDatabase
import com.bulochka.osagocalculator.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AppApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { Repository(database.coefficientDao(), database.dataDao()) }
}