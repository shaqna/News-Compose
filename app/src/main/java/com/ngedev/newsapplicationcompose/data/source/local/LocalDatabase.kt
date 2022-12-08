package com.ngedev.newsapplicationcompose.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ngedev.newsapplicationcompose.data.source.local.dao.ArticleDao
import com.ngedev.newsapplicationcompose.data.source.local.dao.FavoriteDao
import com.ngedev.newsapplicationcompose.data.source.local.dao.RemoteKeysDao
import com.ngedev.newsapplicationcompose.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.domain.model.RemoteKeys
import com.ngedev.newsapplicationcompose.utils.ModelConverter

@Database(
    entities = [Article::class, RemoteKeys::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ModelConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}