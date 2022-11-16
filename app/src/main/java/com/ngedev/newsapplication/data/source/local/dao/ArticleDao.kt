package com.ngedev.newsapplication.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ngedev.newsapplication.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(vararg article: Article)

    @Query("SELECT * FROM article")
    fun getAllArticles(): PagingSource<Int, Article>

    @Query("DELETE FROM article")
    fun deleteAllArticle()


}