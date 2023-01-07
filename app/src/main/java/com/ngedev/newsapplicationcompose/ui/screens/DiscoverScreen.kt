package com.ngedev.newsapplicationcompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.BuildCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ngedev.newsapplicationcompose.R
import com.ngedev.newsapplicationcompose.ui.components.SearchView
import com.ngedev.newsapplicationcompose.ui.components.ListArticle

@BuildCompat.PrereleaseSdkCheck
@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.logo_name), fontSize = 30.sp,)
        Spacer(modifier = modifier.height(16.dp))
        SearchView()
        Spacer(modifier = modifier.height(16.dp))
        ListArticle(navController = navController)
    }
}

@BuildCompat.PrereleaseSdkCheck
@Preview(showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
fun DiscoverScreenPreview() {
    MaterialTheme {
        DiscoverScreen(navController = rememberNavController())
    }
}