package com.ngedev.newsapplication.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ngedev.newsapplication.domain.model.Source

@Entity(tableName = "favorite")
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val isFavorite: Boolean = false
)
