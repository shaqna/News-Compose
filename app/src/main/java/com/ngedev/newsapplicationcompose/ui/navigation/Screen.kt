package com.ngedev.newsapplicationcompose.ui.navigation

sealed class Screen(val route: String) {
    object Discover: Screen("discover")
    object Bookmark: Screen("favorite")
    object Profile: Screen("profile")
}
