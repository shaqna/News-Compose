package com.ngedev.newsapplicationcompose.ui.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ngedev.newsapplicationcompose.R
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.domain.model.Source
import com.ngedev.newsapplicationcompose.ui.components.DetailContent
import com.ngedev.newsapplicationcompose.ui.navigation.Screen
import com.ngedev.newsapplicationcompose.ui.viewmodel.DetailViewModel
import com.ngedev.newsapplicationcompose.utils.DataMapper.toFavoriteEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

// create detail screen
@Composable
fun DetailScreen(
    article: Article?,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = koinViewModel()
) {
    viewModel.getArticleByTitle(article?.title ?: "")
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    var favoriteStatus by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            launch {
                viewModel.isFavorite.collectLatest {
                    favoriteStatus = it
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                onBackPressed = {
                    navController.navigate(Screen.Discover.route) {
                        popUpTo(Screen.Detail.route) {
                            inclusive = true
                        }
                    }
                },
                onFavoriteClick = {
                    favoriteStatus = !favoriteStatus
                    if (favoriteStatus) {
                        val favoriteArticle = article?.toFavoriteEntity(true)
                        viewModel.addArticleFavorite(favoriteArticle!!)
                    } else {
                        val favoriteArticle = article?.toFavoriteEntity(false)
                        viewModel.deleteArticleFavorite(favoriteArticle!!)
                    }
                },
                isFavorite = favoriteStatus
            )
        },
        content = { innerPadding ->
            DetailContent(
                innerPadding = innerPadding,
                article = article,
                navController = navController
            )
        }
    )

}

@Composable
fun AppBar(
    onBackPressed: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean
) {
    TopAppBar(
        title = {},
        elevation = 4.dp,
        backgroundColor = Color.White,
        navigationIcon = {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        }, actions = {
            IconButton(onClick = {
                onFavoriteClick()
            }) {
                if (isFavorite) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_bookmark_added_24),
                        null
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_bookmark_border_24),
                        null
                    )
                }

            }
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.Share, null)
            }
        })
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview() {

    MaterialTheme {
        DetailScreen(
            article = Article(
                source = Source(),
                urlToImage = "",
                author = stringResource(id = R.string.dummy_article_author),
                content = stringResource(id = R.string.dummy_article_content),
                description = stringResource(id = R.string.dummy_article_description),
                publishedAt = stringResource(id = R.string.dummy_article_date),
                title = stringResource(id = R.string.dummy_article_title)
            ),
            navController = rememberNavController()

        )
    }
}