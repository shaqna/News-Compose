package com.ngedev.newsapplicationcompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ngedev.newsapplicationcompose.databinding.ActivityMainBinding


@BuildCompat.PrereleaseSdkCheck
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loadKoinModules(DiscoverViewModel.inject())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(navView,navController)

//        setContent {
//            MaterialTheme {
//                MainApp()
//            }
//        }
    }


}

//@BuildCompat.PrereleaseSdkCheck
//@Composable
//fun MainApp(
//    modifier: Modifier = Modifier,
//    navController: NavHostController = rememberNavController(),
//    viewModel: DiscoverViewModel = koinViewModel()
//) {
//    val context = LocalContext.current
//    val articles = viewModel.listArticle.collectAsLazyPagingItems()
//
//    Scaffold(
//        bottomBar = {
//            BottomBar(navController = navController)
//        },
//        modifier = modifier
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = Screen.Discover.route,
//            modifier = modifier.padding(innerPadding)
//        ) {
//            composable(Screen.Discover.route) {
//                AndroidViewBinding(FragmentDiscoverBinding::inflate) {
//
//                    //content not shown, bug?
//                    composeListView.apply {
//                        setContent {
//                            MaterialTheme {
//                                ListArticle(context = context, items = articles)
//                            }
//                        }
//                    }
//                }
//            }
//            composable(Screen.Bookmark.route) {
//                AndroidViewBinding(FragmentFavoriteBinding::inflate)
//            }
//            composable(Screen.Profile.route) {
//                AndroidViewBinding(FragmentProfileBinding::inflate) {
//
//                    // content shown
//                    profileCompose.setContent {
//                        MaterialTheme {
//                            Profile()
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomBar(
//    modifier: Modifier = Modifier,
//    navController: NavHostController,
//) {
//    BottomNavigation(
//        modifier = modifier
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//
//        val navigationItems = listOf(
//            NavigationItem(
//                title = stringResource(id = R.string.title_discover),
//                icon = ImageVector.vectorResource(id = R.drawable.asterisk),
//                screen = Screen.Discover
//            ),
//            NavigationItem(
//                title = stringResource(id = R.string.title_bookmark),
//                icon = ImageVector.vectorResource(id = R.drawable.bookmark_multiple_outline),
//                screen = Screen.Bookmark
//            ),
//            NavigationItem(
//                title = stringResource(id = R.string.title_profile),
//                icon = ImageVector.vectorResource(id = R.drawable.ic_baseline_account_circle_24),
//                screen = Screen.Profile
//            )
//        )
//
//        BottomNavigation(
//            backgroundColor = Color.White
//        ) {
//            navigationItems.map { item ->
//                BottomNavigationItem(
//                    icon = {
//                        Icon(imageVector = item.icon, contentDescription = item.title)
//                    },
//                    label = { Text(item.title) },
//                    selected = currentRoute == item.screen.route,
//                    onClick = {
//                        navController.navigate(item.screen.route) {
//                            popUpTo(navController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
//                            restoreState = true
//                            launchSingleTop = true
//                        }
//                    }
//                )
//            }
//        }
//    }
//}
//
//@BuildCompat.PrereleaseSdkCheck
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MainAppPreview() {
//    MaterialTheme {
//        MainApp()
//    }
//}