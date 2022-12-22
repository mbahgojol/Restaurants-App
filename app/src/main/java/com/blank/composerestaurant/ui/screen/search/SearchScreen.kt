package com.blank.composerestaurant.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blank.composerestaurant.R
import com.blank.composerestaurant.ui.common.UiState
import com.blank.composerestaurant.ui.components.BackButton
import com.blank.composerestaurant.ui.components.CenterProgressBar
import com.blank.composerestaurant.ui.components.EmptyView
import com.blank.composerestaurant.ui.components.ErrorMessage
import com.blank.composerestaurant.ui.screen.home.ShowListRestaurant
import com.blank.composerestaurant.ui.theme.ComposeRestaurantTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    LaunchedEffect(true) {
        viewModel.getAllRestaurant()
    }

    Scaffold(modifier = modifier, topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primary, shape = RectangleShape)
                .wrapContentHeight()
        ) {
            BackButton(
                navigateBack = navigateBack,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 4.dp)
            )
            SearchBar(query = viewModel.query, onQueryChange = viewModel::searchRestaurant)
        }
    }) {
        viewModel.uistateList.collectAsState(UiState.Loading).value.let {
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

@Composable
fun SearchBar(
    query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(stringResource(R.string.search_restaurant))
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    ComposeRestaurantTheme {
        SearchBar(query = "", onQueryChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    ComposeRestaurantTheme {
        SearchScreen(navigateBack = {

        }, navigateToDetail = {

        })
    }
}