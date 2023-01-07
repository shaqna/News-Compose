package com.ngedev.newsapplicationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.core.os.BuildCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ngedev.newsapplicationcompose.ui.navigation.BottomNavigationItem
import com.ngedev.newsapplicationcompose.ui.navigation.NavGraph
import com.ngedev.newsapplicationcompose.ui.navigation.Screen


@BuildCompat.PrereleaseSdkCheck
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainPage()
            }
        }
    }
}

@BuildCompat.PrereleaseSdkCheck
@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavGraph(navController = navController, innerPadding = innerPadding)
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    val navigationItems = listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.title_discover),
            icon = ImageVector.vectorResource(id = R.drawable.asterisk),
            screen = Screen.Discover
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.title_bookmark),
            icon = ImageVector.vectorResource(id = R.drawable.bookmark_multiple_outline),
            screen = Screen.Bookmark
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.title_profile),
            icon = ImageVector.vectorResource(id = R.drawable.ic_baseline_account_circle_24),
            screen = Screen.Profile
        )
    )

    val bottomBarDestination = navigationItems.any {
        it.screen.route == currentRoute?.route
    }

    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = Color.White
        ) {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.title)
                    },
                    label = { Text(item.title) },
                    selected = currentRoute?.hierarchy?.any {
                        it.route == item.screen.route
                    } == true,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
