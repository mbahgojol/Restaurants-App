package com.blank.composerestaurant.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.blank.composerestaurant.R
import com.blank.composerestaurant.ui.navigation.NavigationItem
import com.blank.composerestaurant.ui.navigation.Screen
import com.blank.composerestaurant.ui.screen.about.AboutScreen
import com.blank.composerestaurant.ui.screen.detail.DetailScreen
import com.blank.composerestaurant.ui.screen.favorite.FavoriteScreen
import com.blank.composerestaurant.ui.screen.home.HomeScreen
import com.blank.composerestaurant.ui.screen.search.SearchScreen
import com.blank.composerestaurant.ui.screen.splash.SplashScreen
import com.blank.composerestaurant.ui.theme.ComposeRestaurantTheme

@Composable
private fun BottomBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )

        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(icon = {
                    Icon(
                        imageVector = item.icon, contentDescription = item.title
                    )
                },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    })
            }
        }
    }
}

@Composable
fun RestoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            when (currentRoute) {
                Screen.Home.route, Screen.Favorite.route, Screen.About.route -> {
                    BottomBar(navController)
                }
            }
        }, modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail = { id ->
                    navController.navigate(Screen.DetailResto.createRoute(id, "remote"))
                }, navigateToSearch = {
                    navController.navigate(Screen.Search.route)
                })
            }
            composable(Screen.Search.route) {
                SearchScreen(navigateBack = {
                    navController.navigateUp()
                }, navigateToDetail = { id ->
                    navController.navigate(Screen.DetailResto.createRoute(id, "remote"))
                })
            }
            composable(Screen.Splash.route) {
                SplashScreen {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            }
            composable(Screen.About.route) {
                AboutScreen(
                    modifier = modifier.padding(16.dp)
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen { id ->
                    navController.navigate(Screen.DetailResto.createRoute(id, "local"))
                }
            }
            composable(
                route = Screen.DetailResto.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType },
                    navArgument("type") { type = NavType.StringType }),
            ) {
                val id = it.arguments?.getString("id") ?: ""
                val type = it.arguments?.getString("type") ?: ""
                DetailScreen(id = id, type = type) {
                    navController.navigateUp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestoAppPreview() {
    ComposeRestaurantTheme {
        RestoApp()
    }
}