package com.ngedev.newsapplicationcompose.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ngedev.newsapplicationcompose.R
import com.ngedev.newsapplicationcompose.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplicationcompose.ui.menu.BookmarkMenu

// create bookmark item view
@Composable
fun BookmarkItem(
    item: FavoriteEntity,
    modifier: Modifier = Modifier,
    onDeleteItem: () -> Unit,
    onClickItem: () -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Card(modifier = modifier.clickable {
        onClickItem()
    }, shape = RoundedCornerShape(12.dp), elevation = 2.dp) {
        Column(modifier = modifier.requiredWidth(150.dp)) {
            AsyncImage(
                model = item.urlToImage,
                contentDescription = stringResource(
                    id = R.string.cd_image_article
                ),
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .requiredHeight(105.dp)
                    .fillMaxWidth()
            )
            Text(
                text = item.title,
                modifier = modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = modifier.padding(start = 8.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.author ?: "",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.weight(3f)
                )
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)
                        .weight(1f)
                ) {
                    IconButton(onClick = {
                        expanded = true
                        Toast.makeText(context, item.author, Toast.LENGTH_LONG).show()
                    }, modifier = modifier) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.cd_image_menu)
                        )
                    }
                    BookmarkMenu(
                        onMenuClick = onDeleteItem,
                        expanded = expanded,
                        onDismiss = {
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkItemPreview() {
    MaterialTheme {
        //BookmarkItem()
    }
}