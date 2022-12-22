package com.blank.composerestaurant.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blank.composerestaurant.R
import com.blank.composerestaurant.ui.theme.largeTitle
import com.blank.composerestaurant.ui.theme.largeTitleBold

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.my_photo),
            contentDescription = stringResource(id = R.string.menu_about),
            modifier = Modifier
                .wrapContentSize()
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Ghozi Mahdi", style = largeTitleBold.copy(
                color = Color.Black
            )
        )

        Text(
            text = "profghozimahdi@gmail.com", style = largeTitle.copy(
                color = Color.Black
            )
        )
    }
}