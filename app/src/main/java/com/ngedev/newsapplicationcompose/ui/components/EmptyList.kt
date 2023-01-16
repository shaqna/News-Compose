package com.ngedev.newsapplicationcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ngedev.newsapplicationcompose.R

// create empty list view component
@Composable
fun EmptyList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_bookmark),
            contentDescription = stringResource(id = R.string.empty_favorite)
        )
        Spacer(modifier = modifier.height(21.dp))
        Text(text = stringResource(id = R.string.description_empty_favorite), fontSize = 18.sp)
    }
}

@Preview(showSystemUi = true)
@Composable
fun EmptyListPreview() {
    MaterialTheme {
        EmptyList()
    }
}