package com.ngedev.newsapplicationcompose.data.source.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.ngedev.newsapplicationcompose.data.source.local.dao.ArticleDao
import com.ngedev.newsapplicationcompose.data.source.local.dao.FavoriteDao
import com.ngedev.newsapplicationcompose.data.source.local.dao.RemoteKeysDao
import com.ngedev.newsapplicationcompose.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.domain.model.RemoteKeys
import kotlinx.coroutines.flow.Flow
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


class LocalDataSource(
    private val articleDao: ArticleDao,
    private val favoriteDao: FavoriteDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val db: LocalDatabase
) {

    suspend fun <R> runAsTransaction(block: suspend () -> R): R {
        return db.withTransaction(block)
    }

    suspend fun insertAll(remoteKey: List<RemoteKeys>) = remoteKeysDao.insertAll(remoteKey)

    suspend fun getRemoteKeysId(id: Int): RemoteKeys? = remoteKeysDao.getRemoteKeysId(id)

    suspend fun deleteRemoteKeys() = remoteKeysDao.deleteRemoteKeys()

    suspend fun insertArticle(vararg article: Article) = articleDao.insertArticle(*article)

    fun getAllArticles(): PagingSource<Int, Article> = articleDao.getAllArticles()

    fun deleteAllArticle() = articleDao.deleteAllArticle()

    fun getAllFavoriteArticles(): Flow<List<FavoriteEntity>> = favoriteDao.selectAllFavorite()

    fun getArticleByTitle(title: String): Flow<Article> = favoriteDao.selectArticleByTitle(title)

    suspend fun insertFavoriteArticle(favoriteEntity: FavoriteEntity) = favoriteDao.insertFavoriteArticle(favoriteEntity)

    suspend fun deleteFavoriteArticle(title: String) = favoriteDao.deleteFavoriteArticle(title)

    companion object {
        fun inject() = module {
            factoryOf(::LocalDataSource)
        }
    }
}