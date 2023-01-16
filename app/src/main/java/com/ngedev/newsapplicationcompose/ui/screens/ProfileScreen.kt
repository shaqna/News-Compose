package com.ngedev.newsapplicationcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ngedev.newsapplicationcompose.R

// create profile screen
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier
    ) {
        val (cardProfile, cardAbout) = createRefs()

        Card(
            modifier = modifier
                .constrainAs(cardProfile) {
                    width = Dimension.matchParent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .padding(12.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 4.dp
        ) {
            Column {
                Box(
                    modifier = modifier.height(235.dp)
                ) {
                    Image(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        painter = painterResource(id = R.drawable.profile_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Image(
                        modifier = modifier
                            .padding(start = 32.dp)
                            .size(150.dp)
                            .align(Alignment.BottomStart)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.profile_image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }

                Column(modifier = modifier.padding(start = 32.dp, bottom = 16.dp)) {
                    Text(
                        text = "Shaquille Rizki Ramadhan Na",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "shaquillerizkirn@gmail.com",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Card(
            modifier = modifier
                .constrainAs(cardAbout) {
                    width = Dimension.matchParent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(cardProfile.bottom)
                }
                .padding(12.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier.padding(start = 12.dp, end = 12.dp)
            ) {
                Text(
                    text = "About",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = modifier.padding(top = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.about_developer),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = modifier.padding(top = 16.dp, bottom = 16.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfilePreview() {
    MaterialTheme {
        ProfileScreen()
    }
}