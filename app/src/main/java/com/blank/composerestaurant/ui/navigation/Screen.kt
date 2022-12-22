package com.blank.composerestaurant.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Splash : Screen("splash")
    object Favorite : Screen("favorite")
    object Search : Screen("search")
    object About : Screen("about")
    object DetailResto : Screen("home/{id}/{type}") {
        fun createRoute(id: String, type: String) = "home/$id/$type"
    }
}