package com.ngedev.newsapplicationcompose.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.ngedev.newsapplicationcompose.utils.JsonNavType
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


class ArticleArgType: JsonNavType<Article>() {
    override fun fromJsonParse(value: String): Article = Gson().fromJson(value, Article::class.java)

    override fun Article.getJsonParse(): String = Gson().toJson(this)

}