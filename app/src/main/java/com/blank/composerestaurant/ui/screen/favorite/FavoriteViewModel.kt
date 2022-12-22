package com.blank.composerestaurant.ui.screen.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.composerestaurant.data.Repository
import com.blank.composerestaurant.data.model.ResponseListRestaurant
import com.blank.composerestaurant.data.model.Restaurant
import com.blank.composerestaurant.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val liveDataList: LiveData<List<Restaurant>> get() = repository.getListRestaurantFavorite()
}