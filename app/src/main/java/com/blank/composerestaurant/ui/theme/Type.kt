package com.blank.composerestaurant.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)

val largeTitle = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 25.sp,
    fontFamily = FontFamily.Default
)
val largeTitleBold = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 25.sp,
    fontFamily = FontFamily.Default
)