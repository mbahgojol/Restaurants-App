package com.blank.composerestaurant.data

import androidx.lifecycle.LiveData
import com.blank.composerestaurant.data.model.ResponseListRestaurant
import com.blank.composerestaurant.data.model.Restaurant
import com.blank.composerestaurant.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getListRestaurant(): Flow<UiState<ResponseListRestaurant>>
    fun getDetailRestaurant(id: String): Flow<UiState<Restaurant>>
    fun favoriteRestaurant(restaurant: Restaurant): Flow<Boolean>
    fun deleteRestaurant(restaurant: Restaurant): Flow<Boolean>
    fun searchRestaurant(q: String): Flow<UiState<ResponseListRestaurant>>
    fun getDetailRestaurantFavorite(id: String): Flow<UiState<Restaurant>>
    fun getListRestaurantFavorite(): LiveData<List<Restaurant>>
}