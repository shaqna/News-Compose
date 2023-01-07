package com.ngedev.newsapplicationcompose.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.BuildCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.gson.Gson
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.domain.model.ArticleArgType
import com.ngedev.newsapplicationcompose.ui.screens.BookmarkScreen
import com.ngedev.newsapplicationcompose.ui.screens.DetailScreen
import com.ngedev.newsapplicationcompose.ui.screens.DiscoverScreen
import com.ngedev.newsapplicationcompose.ui.screens.ProfileScreen




@BuildCompat.PrereleaseSdkCheck
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Discover.route,
        modifier = modifier.padding(innerPadding)
    ) {
        composable(Screen.Discover.route) {
            DiscoverScreen(navController = navController)
        }

        composable(Screen.Bookmark.route) {
            BookmarkScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        detailNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = Screen.Detail.route
    ) {
        composable(Screen.Detail.route,) {
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<Article>(
                DETAIL_ARGUMENT_KEY
            )
            DetailScreen(article = result, navController = navController)
        }
    }
}

object Graph {
    const val DETAILS = "details_graph"
}