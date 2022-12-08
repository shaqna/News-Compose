package com.ngedev.newsapplicationcompose.ui.discover.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.BuildCompat
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.ui.detail.DetailActivity

@BuildCompat.PrereleaseSdkCheck
@Composable
fun ListArticle(
    context: Context,
    items: LazyPagingItems<Article>,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = items,
            key = {
                it.id
            }
        ) { article ->
            article?.let {
                ArticleItem(
                    urlToImage = article.urlToImage ?: "",
                    author = article.author ?: "",
                    title = article.title ?: "",
                    publishedAt = article.publishedAt ?: "",
                    onItemClick = {
                        Intent(context, DetailActivity::class.java).also {
                            it.putExtra(DetailActivity.TAG, article)
                            startActivity(context, it, null)
                        }
                    }
                )
            }

        }
    }
}