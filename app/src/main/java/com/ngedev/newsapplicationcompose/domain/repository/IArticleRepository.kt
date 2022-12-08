package com.ngedev.newsapplicationcompose.domain.repository

import androidx.paging.PagingData
import com.ngedev.newsapplicationcompose.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.ui.detail.BookmarkState
import kotlinx.coroutines.flow.Flow

interface IArticleRepository {
    fun getArticleRelateWith(query: String): Flow<PagingData<Article>>

    fun loadFavoriteArticles(): Flow<List<FavoriteEntity>>

    fun getArticleByTitle(title: String): Flow<BookmarkState<Article>>

    suspend fun insertFavoriteArticle(favoriteEntity: FavoriteEntity)

    suspend fun deleteFavoriteArticle(favoriteEntity: FavoriteEntity)
}