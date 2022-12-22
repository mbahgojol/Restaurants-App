package com.blank.composerestaurant.ui.screen.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.blank.composerestaurant.R
import com.blank.composerestaurant.data.model.Restaurant
import com.blank.composerestaurant.ui.common.UiState
import com.blank.composerestaurant.ui.components.*
import com.blank.composerestaurant.ui.theme.ComposeRestaurantTheme
import com.blank.composerestaurant.ui.theme.Orange

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    id: String,
    type: String,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    navigateBack: () -> Unit,
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                when (type) {
                    "local" -> {
                        viewModel.getRestaurantFavorite(id)
                    }
                    else -> {
                        viewModel.getRestaurant(id)
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(id) {
        viewModel.stateSaveFavorite.observe(lifecycleOwner) {
            if (it) {
                Toast.makeText(
                    context, "Berhasil Menambahkan kedalam Favorite", Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.stateDeleteFavorite.observe(lifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Berhasil delete Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val manageStateRestaurant by viewModel.stateDetailRestaurant.collectAsState()
    CustomScaffold(
        title = stringResource(id = R.string.detail_restaurant), navigateBack = navigateBack
    ) {
        manageStateRestaurant.let {
            when (it) {
                is UiState.Loading -> {
                    CenterProgressBar()
                }
                is UiState.Success -> {
                    val response: Restaurant = it.data
                    var isFavorite by rememberSaveable {
                        mutableStateOf(response.isFavorite)
                    }

                    ShowDetailRestaurants(model = response, isFavorite = isFavorite) { resto ->
                        isFavorite = !isFavorite
                        if (isFavorite) {
                            viewModel.saveFavorite(resto)
                        } else {
                            viewModel.deleteFavorite(resto)
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorMessage(msg = it.errorMessage)
                }
            }
        }
    }
}

@Composable
fun ShowDetailRestaurants(
    modifier: Modifier = Modifier,
    model: Restaurant,
    isFavorite: Boolean,
    onClickFavorite: (Restaurant) -> Unit = {},
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        HeaderDetailScreen(img = model.largeImg,
            modifier = Modifier.wrapContentHeight(),
            isFavorite = isFavorite,
            onClickFavorite = {
                onClickFavorite(model)
            })
        SpacerHeight(height = 16.dp)
        Text(
            text = model.name,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .wrapContentHeight()
        )
        SpacerHeight(height = 10.dp)
        RatingBar(
            rating = model.rating,
            starsColor = Orange,
            modifier = Modifier
                .height(18.dp)
                .padding(horizontal = 16.dp)
        )
        SpacerHeight(height = 16.dp)
        Text(
            text = model.description,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .wrapContentHeight()
        )
    }
}

@Composable
fun HeaderDetailScreen(
    modifier: Modifier = Modifier,
    img: String? = null,
    onClickFavorite: () -> Unit = {},
    isFavorite: Boolean = false
) {
    ConstraintLayout(modifier = modifier) {
        val (image, btnFavorite) = createRefs()

        Image(painter = if (img == null) painterResource(id = R.drawable.ic_launcher_background)
        else rememberAsyncImagePainter(
            model = img
        ), contentScale = ContentScale.Crop, contentDescription = stringResource(
            id = R.string.image_resto
        ), modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .constrainAs(image) {
                top.linkTo(parent.top)
                bottom.linkTo(btnFavorite.bottom, margin = 25.dp)
            })
        FloatingActionButton(onClick = {
            onClickFavorite()
        }, modifier = Modifier.constrainAs(btnFavorite) {
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end, margin = 16.dp)
        }, backgroundColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = stringResource(id = R.string.favorite),
                tint = if (isFavorite) Color.Red else Color.LightGray
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 200, heightDp = 300)
@Composable
fun DetailScreenPreview() {
    ComposeRestaurantTheme {
        HeaderDetailScreen(
            modifier = Modifier.wrapContentHeight()
        )
    }
}