package com.blank.composerestaurant.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.blank.composerestaurant.R
import com.blank.composerestaurant.ui.theme.ComposeRestaurantTheme

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    navigateBack: () -> Unit,
) {
    IconButton(onClick = navigateBack, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            tint = color
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BackButtonPreview() {
    ComposeRestaurantTheme {
        BackButton {

        }
    }
}