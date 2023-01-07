package com.ngedev.newsapplicationcompose

import android.app.Application
import com.ngedev.newsapplicationcompose.data.di.DataInjection
import com.ngedev.newsapplicationcompose.domain.di.DomainInjection
import com.ngedev.newsapplicationcompose.ui.viewmodel.DetailViewModel
import com.ngedev.newsapplicationcompose.ui.viewmodel.DiscoverViewModel
import com.ngedev.newsapplicationcompose.ui.viewmodel.FavoriteViewModel
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
            modules(
                listOf(
                    DataInjection.injects(),
                    DomainInjection.injects(),
                    DiscoverViewModel.inject(),
                    FavoriteViewModel.inject(),
                    DetailViewModel.inject()
                )
            )
        }
    }
}