package com.example.runtracker

import android.app.Application
import com.example.auth.data.di.authDataModule
import com.example.auth.presentation.di.authViewModelModule
import com.example.core.data.di.coreDataModule
import com.example.runtracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunTrackerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        // Timber
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree()) // to enable use timber for logging
        }

        // Koin
        startKoin {
            androidLogger()
            androidContext(this@RunTrackerApp)
            modules(
                appModule,
                authDataModule,
                authViewModelModule,
                coreDataModule
            )
        }
    }
}