package com.ngedev.newsapplicationcompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.os.BuildCompat
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ngedev.newsapplicationcompose.ui.navigation.DETAIL_ARGUMENT_KEY
import com.ngedev.newsapplicationcompose.ui.navigation.Screen
import com.ngedev.newsapplicationcompose.ui.viewmodel.DiscoverViewModel
import org.koin.androidx.compose.koinViewModel

@BuildCompat.PrereleaseSdkCheck
@Composable
fun ListArticle(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DiscoverViewModel = koinViewModel(),
) {
    val items = viewModel.listArticle.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item(key = "refresh_loading") {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xff0b698b)
                            )
                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item(key = "append_loading") {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xff0b698b)
                            )
                        }
                    }
                }
                loadState.append is LoadState.Error -> {
                    item(key = "append_error") {

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = modifier.fillMaxSize()
                        ) {
                            Text(text = "Error")
                        }
                    }
                }
                else -> {
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
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        key = DETAIL_ARGUMENT_KEY,
                                        value = article
                                    )
                                    navController.navigate(Screen.Detail.route)
                                }
                            )
                        }

                    }
                }
            }
        }

    }
}