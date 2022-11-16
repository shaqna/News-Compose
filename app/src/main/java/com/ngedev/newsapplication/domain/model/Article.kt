package com.ngedev.newsapplication.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "article")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val source: Source,
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = "",
): Parcelable
