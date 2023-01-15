package com.ngedev.newsapplicationcompose.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ngedev.newsapplicationcompose.R
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.ui.navigation.Screen

@Composable
fun DetailContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    article: Article?,
    context: Context = LocalContext.current,
) {
    val paddingValues = PaddingValues(
        start = innerPadding.calculateStartPadding(LayoutDirection.Rtl),
        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr)
    )

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = article?.title ?: "",
            fontSize = 24.sp,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )

        AsyncImage(
            modifier = modifier
                .height(212.dp)
                .fillMaxWidth()
                .padding(top = 16.dp),
            model = article?.urlToImage,
            contentDescription = stringResource(id = R.string.cd_image_article),
            contentScale = ContentScale.FillWidth,

            )

        Text(
            text = article?.description ?: "",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Justify
        )

        Divider(modifier = modifier.padding(top = 16.dp), color = Color.LightGray, thickness = 1.dp)

        Row(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier.padding(vertical = 4.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_person_24),
                contentDescription = stringResource(R.string.cd_author_image)
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = article?.author ?: "",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = modifier
                    .width(IntrinsicSize.Max)
                    .padding(vertical = 4.dp)
            )
            Spacer(modifier = modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.dot_symbol),
                modifier = modifier
                    .width(IntrinsicSize.Max)
                    .padding(vertical = 4.dp),
                color = Color.Gray
            )
            Spacer(modifier = modifier.width(4.dp))
            Image(
                modifier = modifier.padding(vertical = 4.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_calendar_today_24),
                contentDescription = stringResource(R.string.cd_author_image)
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = article?.publishedAt ?: "",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = modifier
                    .width(IntrinsicSize.Max)
                    .padding(vertical = 4.dp)
            )
        }

        Divider(
            modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

        Text(
            text = article?.content ?: "",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Justify
        )

        Button(
            onClick = {

                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "url",
                    value = article?.url
                )
                navController.navigate(Screen.Web.route)
            },
            modifier = modifier.fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0B698B))
        ) {
            Text(text = stringResource(id = R.string.read_more), color = Color.White)
        }
    }
}