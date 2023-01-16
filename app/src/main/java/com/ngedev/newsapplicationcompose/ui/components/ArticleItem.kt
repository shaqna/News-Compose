package com.ngedev.newsapplicationcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.ngedev.newsapplicationcompose.R

// create ArticleItem view
@Composable
fun ArticleItem(
    urlToImage: String,
    author: String,
    title: String,
    publishedAt: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .clickable { onItemClick() }
            .fillMaxWidth()
    ) {
        val (cardView, articleTitle, articleAuthor, dotSymbol, articleDatePublished, divider) = createRefs()

        Card(
            modifier = modifier
                .constrainAs(cardView) {
                    start.linkTo(parent.start, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(parent.top, margin = 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(212.dp)
                },
            shape = RoundedCornerShape(12.dp)
        ) {
            AsyncImage(
                contentScale = ContentScale.FillBounds,
                model = urlToImage,
                contentDescription = stringResource(id = R.string.cd_image_article)
            )
        }



        Text(
            text = title,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            modifier = modifier.constrainAs(articleTitle) {
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(cardView.bottom, margin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = author,
            modifier = modifier.constrainAs(articleAuthor) {
                start.linkTo(articleTitle.start)
                top.linkTo(articleTitle.bottom, margin = 8.dp)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
            color = Color.Gray
        )

        Text(
            text = stringResource(id = R.string.dot_symbol),
            modifier = modifier.constrainAs(dotSymbol) {
                start.linkTo(articleAuthor.end, margin = 8.dp)
                top.linkTo(articleAuthor.top)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
            color = Color.Gray
        )

        Text(
            text = publishedAt,
            modifier = modifier.constrainAs(articleDatePublished) {
                start.linkTo(dotSymbol.end, margin = 8.dp)
                top.linkTo(articleAuthor.top)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
            color = Color.Gray
        )

        Divider(modifier = modifier
            .constrainAs(divider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(articleDatePublished.bottom, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            },
            color = Color.LightGray, thickness = 1.dp
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3, showSystemUi = true)
@Composable
fun ArticleItemPreview() {
    MaterialTheme {
        ArticleItem(
            urlToImage = "",
            author = stringResource(id = R.string.dummy_article_author),
            title = stringResource(id = R.string.dummy_article_title),
            publishedAt = stringResource(id = R.string.dummy_article_date),
            onItemClick = {}
        )
    }
}