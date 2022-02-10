package com.bulochka.osagocalculator

import android.app.Application
import com.bulochka.osagocalculator.di.appModule
import com.bulochka.osagocalculator.di.localModule
import com.bulochka.osagocalculator.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(listOf(appModule, localModule, networkModule))
        }
    }
}