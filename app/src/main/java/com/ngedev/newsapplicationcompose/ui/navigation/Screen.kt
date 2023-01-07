package com.ngedev.newsapplicationcompose.ui.navigation

import com.ngedev.newsapplicationcompose.domain.model.Article

const val DETAIL_ARGUMENT_KEY = "article"

sealed class Screen(val route: String) {
    object Discover: Screen("discover_screen")
    object Bookmark: Screen("favorite_screen")
    object Profile: Screen("profile_screen")
    object Detail: Screen("detail_screen")
}
