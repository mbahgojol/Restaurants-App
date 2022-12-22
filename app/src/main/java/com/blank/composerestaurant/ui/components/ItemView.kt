package com.blank.composerestaurant.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.blank.composerestaurant.R
import com.blank.composerestaurant.data.model.Restaurant
import com.blank.composerestaurant.ui.theme.ComposeRestaurantTheme
import com.blank.composerestaurant.ui.theme.Orange
import com.blank.composerestaurant.ui.theme.Typography

@Composable
fun ItemView(
    modifier: Modifier = Modifier, model: Restaurant? = null
) {
    Row(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(model = model?.smallImg),
            contentDescription = stringResource(id = R.string.image_resto),
            modifier = Modifier
                .height(60.dp)
                .width(60.dp),
            contentScale = ContentScale.Crop
        )
        SpacerWidth(width = 10.dp)
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            Text(text = model?.name.toString())
            Text(text = model?.city.toString(), style = Typography.caption)
            SpacerWidth(width = 10.dp)
            RatingBar(
                rating = model?.rating ?: 0.0,
                starsColor = Orange,
                modifier = Modifier.height(18.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemViewPreview() {
    ComposeRestaurantTheme {
        ItemView()
    }
}