package com.ngedev.newsapplicationcompose.domain.usecase

import androidx.paging.PagingData
import com.ngedev.newsapplicationcompose.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.domain.repository.IArticleRepository
import com.ngedev.newsapplicationcompose.ui.viewmodel.BookmarkState
import kotlinx.coroutines.flow.Flow
import org.koin.core.module.dsl.bind

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

class ArticleInteractor(private val repository: IArticleRepository): ArticleUseCase {
    override fun getArticlesRelateWith(query: String): Flow<PagingData<Article>> =
        repository.getArticleRelateWith(query)

    override fun loadFavoriteArticles(): Flow<List<FavoriteEntity>> =
        repository.loadFavoriteArticles()

    override fun getArticleByTitle(title: String): Flow<BookmarkState<Article>> =
        repository.getArticleByTitle(title)

    override suspend fun insertFavoriteArticle(favoriteEntity: FavoriteEntity) {
        repository.insertFavoriteArticle(favoriteEntity)
    }

    override suspend fun deleteFavoriteArticle(favoriteEntity: FavoriteEntity) {
        repository.deleteFavoriteArticle(favoriteEntity)
    }

    companion object {
        fun inject() = module {
            factoryOf(::ArticleInteractor) {
                bind<ArticleUseCase>()
            }
        }
    }
}