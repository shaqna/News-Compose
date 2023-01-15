package com.ngedev.newsapplicationcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ngedev.newsapplicationcompose.R
import com.ngedev.newsapplicationcompose.ui.components.ListBookmark


@Composable
fun BookmarkScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.favorite_page_name), fontSize = 20.sp,)
        Spacer(modifier = modifier.height(24.dp))
        ListBookmark(navController = navController)
    }
}

@Preview(showSystemUi = true)
@Composable
fun BookmarkScreenPreview() {
    MaterialTheme {
        BookmarkScreen(navController = rememberNavController())
    }
}