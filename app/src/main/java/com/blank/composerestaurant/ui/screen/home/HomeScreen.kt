package com.blank.composerestaurant.ui.screen.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blank.composerestaurant.R
import com.blank.composerestaurant.data.model.Restaurant
import com.blank.composerestaurant.ui.common.UiState
import com.blank.composerestaurant.ui.components.*
import com.blank.composerestaurant.ui.theme.ComposeRestaurantTheme
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
    navigateToSearch: () -> Unit
) {
    LaunchedEffect(true) {
        viewModel.getAllRestaurant()
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.menu_home))
        }, actions = {
            IconButton(onClick = {
                navigateToSearch()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        })
    }, modifier = modifier) {
        viewModel.uistateList.observeAsState(UiState.Loading).value.let {
            when (it) {
                is UiState.Loading -> {
                    CenterProgressBar()
                }
                is UiState.Success -> {
                    val list = it.data.restaurants
                    if (list.isEmpty()) {
                        EmptyView()
                    } else {
                        ShowListRestaurant(
                            list = list, navigateToDetail = navigateToDetail
                        )
                    }
                }
                is UiState.Error -> {
                    ErrorMessage(msg = it.errorMessage)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowListRestaurant(
    modifier: Modifier = Modifier, list: List<Restaurant>, navigateToDetail: (String) -> Unit
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .testTag("list"), contentPadding = PaddingValues(
                bottom = 80.dp, top = 16.dp, start = 16.dp, end = 16.dp
            ), state = listState
        ) {
            items(list, key = { it.id }) { model ->
                ItemView(model = model,
                    modifier = Modifier
                        .animateItemPlacement(tween(durationMillis = 100))
                        .clickable {
                            navigateToDetail(model.id)
                        })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        AnimatedVisibility(
            visible = showButton,
            exit = fadeOut() + slideOutVertically(),
            enter = fadeIn() + slideInVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(onClick = {
                scope.launch {
                    listState.scrollToItem(0)
                }
            })
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White, contentColor = MaterialTheme.colors.primary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeRestaurantTheme {
        ScrollToTopButton(onClick = { })
    }
}