package com.ngedev.newsapplication

import android.app.Application
import com.ngedev.newsapplication.data.di.DataInjection
import com.ngedev.newsapplication.domain.di.DomainInjection
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@NewsApplication)
            loadKoinModules(
                listOf(
                    DataInjection.injects(),
                    DomainInjection.injects()
                )
            )
        }
    }
}