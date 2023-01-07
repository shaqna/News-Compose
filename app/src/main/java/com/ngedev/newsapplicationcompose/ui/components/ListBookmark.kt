package com.ngedev.newsapplicationcompose.ui.components

import android.content.Intent
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
import androidx.core.content.ContextCompat.startActivity
import com.ngedev.newsapplicationcompose.ui.viewmodel.FavoriteViewModel
import com.ngedev.newsapplicationcompose.ui.web.WebActivity
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListBookmark(
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
                    Intent(context, WebActivity::class.java).also { intent ->
                        item.url.let {
                            intent.putExtra(WebActivity.TAG, it)
                        }
                        startActivity(context, intent, null)
                    }
                })
            }


        }
    }


}
