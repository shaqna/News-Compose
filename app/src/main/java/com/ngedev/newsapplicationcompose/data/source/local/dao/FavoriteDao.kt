package com.ngedev.newsapplicationcompose.data.source.local.dao

import androidx.room.*
import com.ngedev.newsapplicationcompose.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplicationcompose.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite WHERE isFavorite = 1")
    fun selectAllFavorite(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE title = :title ")
    fun selectArticleByTitle(title: String): Flow<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteArticle(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE title = :title")
    suspend fun deleteFavoriteArticle(title: String)
}