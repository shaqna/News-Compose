package com.ngedev.newsapplicationcompose.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ngedev.newsapplicationcompose.domain.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(vararg article: Article)

    @Query("SELECT * FROM article")
    fun getAllArticles(): PagingSource<Int, Article>

    @Query("DELETE FROM article")
    fun deleteAllArticle()


}