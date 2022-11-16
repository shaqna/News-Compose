package com.ngedev.newsapplication.domain.di

import com.ngedev.newsapplication.data.repository.ArticleRepository
import com.ngedev.newsapplication.domain.usecase.ArticleInteractor
import org.koin.dsl.module

object DomainInjection {
    fun injects() = module {
        includes(ArticleInteractor.inject())
    }
}