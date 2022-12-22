package com.blank.composerestaurant.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.blank.composerestaurant.assertCurrentRouteName
import com.blank.composerestaurant.ui.navigation.Screen
import com.blank.composerestaurant.ui.theme.ComposeRestaurantTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RestoAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ComposeRestaurantTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                RestoApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Splash.route)
    }
}