package com.ngedev.newsapplicationcompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ngedev.newsapplicationcompose.ui.navigation.Screen
import com.ngedev.newsapplicationcompose.ui.viewmodel.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

// create list bookmark view
@Composable
fun ListBookmark(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = koinViewModel()
) {
    val bookmarkList by viewModel.favorites.collectAsState()
    val context = LocalContext.current

    if (bookmarkList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmptyList()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(items = bookmarkList, key = {
                it.id
            }) { item ->
                BookmarkItem(item = item, onDeleteItem = {
                    viewModel.deleteArticleFavorite(item.copy(isFavorite = false))
                }, onClickItem = {

                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "url",
                        value = item.url
                    )
                    navController.navigate(Screen.Web.route)
                })
            }


        }
    }


}
