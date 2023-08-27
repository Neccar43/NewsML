package com.novacodestudios.news.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ThumbUpOffAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.novacodestudios.news.ui.theme.NewsTheme
import com.novacodestudios.news.util.BottomNavigationItem
import com.novacodestudios.news.util.Const.BASE_URL
import com.novacodestudios.news.util.Const.NEWS_URL
import com.novacodestudios.news.util.Screen
import com.novacodestudios.recall.presentation.settings.SettingsScreen
import com.novacodestudios.recall.presentation.sign_up.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = ""
        setContent {
            NewsTheme {
                val items = listOf(
                    BottomNavigationItem(
                        title = "Ana sayfa",
                        selectedIcon = Icons.Filled.Home,
                        unSelectedIcon = Icons.Outlined.Home,
                        route = Screen.NewsFeedScreen.route
                    ),
                    BottomNavigationItem(
                        title = "Beğenilen Haberler",
                        selectedIcon = Icons.Filled.ThumbUpOffAlt,
                        unSelectedIcon = Icons.Outlined.ThumbUpOffAlt,
                        route = Screen.LikedNewsScreen.route
                    ),
                    BottomNavigationItem(
                        title = "Ayarlar",
                        selectedIcon = Icons.Filled.Settings,
                        unSelectedIcon = Icons.Outlined.Settings,
                        route = Screen.SettingsScreen.route
                    ),
                )
                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    Scaffold(
                        bottomBar = {
                            if (currentDestination != null) {
                                when (currentDestination.route) {
                                    Screen.NewsFeedScreen.route,
                                    Screen.LikedNewsScreen.route,
                                    Screen.SettingsScreen.route,
                                    -> {
                                        NavigationBar {
                                            items.forEachIndexed { index, item ->
                                                NavigationBarItem(
                                                    label = { Text(text = item.title) },
                                                    alwaysShowLabel = false,
                                                    selected = selectedItemIndex == index,
                                                    onClick = {
                                                        selectedItemIndex = index
                                                        navController.navigate(item.route) {
                                                            popUpTo(navController.graph.findStartDestination().id) {
                                                                saveState = true
                                                            }
                                                            launchSingleTop = true
                                                            restoreState = true
                                                        }
                                                    },
                                                    icon = {
                                                        Icon(
                                                            imageVector = if (selectedItemIndex == index) {
                                                                item.selectedIcon
                                                            } else {
                                                                item.unSelectedIcon
                                                            },
                                                            contentDescription = item.title
                                                        )
                                                    })
                                            }
                                        }
                                    }

                                    else -> {}
                                }

                            }

                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination =   Screen.SignInScreen.route,
                            modifier = Modifier.padding(it)
                        ) {
                            composable(route = Screen.SignUpScreen.route) {
                                SignUpScreen(navController = navController)
                            }
                            composable(route = Screen.SignInScreen.route) {
                                SignInScreen(navController = navController)
                            }
                            composable(Screen.NewsFeedScreen.route) {
                                NewsFeedScreen(navController = navController)
                            }
                            composable(Screen.SettingsScreen.route) {
                                SettingsScreen(navController = navController)
                            }
                            composable(Screen.LikedNewsScreen.route) {
                                LikedNewsScreen(navController = navController)
                            }
                            composable(
                                route = Screen.BrowserScreen.route + "/{$NEWS_URL}",
                                arguments = listOf(navArgument(NEWS_URL) {
                                    type = NavType.StringType
                                })
                            ) {

                                BrowserScreen(navController = navController)
                            }

                        }
                    }
                }
            }
        }
    }
}


