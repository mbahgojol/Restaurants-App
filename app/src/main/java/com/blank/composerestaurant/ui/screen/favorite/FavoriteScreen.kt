package com.blank.composerestaurant.ui.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.blank.composerestaurant.R
import com.blank.composerestaurant.ui.components.CustomScaffold
import com.blank.composerestaurant.ui.components.EmptyView
import com.blank.composerestaurant.ui.screen.home.ShowListRestaurant

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
) {
    CustomScaffold(title = stringResource(id = R.string.favorite)) {
        viewModel.liveDataList.observeAsState().value?.let {
            if (it.isNullOrEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EmptyView()
                }
            } else {
                ShowListRestaurant(
                    list = it, navigateToDetail = navigateToDetail
                )
            }
        }
    }
}