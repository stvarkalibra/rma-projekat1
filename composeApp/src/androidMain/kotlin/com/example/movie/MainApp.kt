package com.example.movie

import android.app.Application
import com.example.movie.core.di.networkModule
import com.example.movie.core.di.repositoryModule
import com.example.movie.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeDependencyInjection()
    }

    private fun initializeDependencyInjection() {
        startKoin {
            androidContext(this@MainApp)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}