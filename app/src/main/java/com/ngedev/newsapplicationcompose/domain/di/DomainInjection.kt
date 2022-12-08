package com.ngedev.newsapplicationcompose.domain.di

import com.ngedev.newsapplicationcompose.domain.usecase.ArticleInteractor
import org.koin.dsl.module

object DomainInjection {
    fun injects() = module {
        includes(ArticleInteractor.inject())
    }
}