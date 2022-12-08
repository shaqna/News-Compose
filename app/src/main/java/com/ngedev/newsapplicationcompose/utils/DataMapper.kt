package com.ngedev.newsapplicationcompose.utils

import com.ngedev.newsapplicationcompose.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplicationcompose.domain.model.Article

object DataMapper {
    fun Article.toFavoriteEntity(favoriteState: Boolean): FavoriteEntity =
        FavoriteEntity(
            source = this.source,
            author = this.author,
            title = this.title ?: "",
            description = this.description ?: "",
            url = this.url ?: "",
            urlToImage = this.urlToImage ?: "",
            publishedAt = this.publishedAt ?: "",
            content = this.content ?: "",
            isFavorite = favoriteState
        )
}