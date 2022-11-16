package com.ngedev.newsapplication.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ngedev.newsapplication.data.source.local.dao.ArticleDao
import com.ngedev.newsapplication.data.source.local.dao.FavoriteDao
import com.ngedev.newsapplication.data.source.local.dao.RemoteKeysDao
import com.ngedev.newsapplication.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplication.domain.model.Article
import com.ngedev.newsapplication.domain.model.RemoteKeys
import com.ngedev.newsapplication.utils.ModelConverter

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