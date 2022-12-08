package com.ngedev.newsapplicationcompose

import android.app.Application
import com.ngedev.newsapplicationcompose.data.di.DataInjection
import com.ngedev.newsapplicationcompose.domain.di.DomainInjection
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            loadKoinModules(
                listOf(
                    DataInjection.injects(),
                    DomainInjection.injects(),
                )
            )
        }
    }
}