package com.blank.composerestaurant.ui.screen.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.blank.composerestaurant.ui.theme.ComposeRestaurantTheme

@Composable
fun SplashScreen(navigateTo: () -> Unit) {
    Column(verticalArrangement = Arrangement.Center) {
        val composition by rememberLottieComposition(LottieCompositionSpec.Url("https://assets7.lottiefiles.com/packages/lf20_AMBEWz.json"))
        val logoAnimationState =
            animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            navigateTo()
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    ComposeRestaurantTheme {
        SplashScreen {

        }
    }
}