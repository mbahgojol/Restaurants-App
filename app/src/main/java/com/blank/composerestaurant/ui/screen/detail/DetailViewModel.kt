package com.blank.composerestaurant.ui.screen.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.composerestaurant.data.Repository
import com.blank.composerestaurant.data.model.Restaurant
import com.blank.composerestaurant.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _stateDetailRestaurant = MutableStateFlow<UiState<Restaurant>>(UiState.Loading)
    val stateDetailRestaurant: StateFlow<UiState<Restaurant>> get() = _stateDetailRestaurant

    val stateSaveFavorite = MutableLiveData<Boolean>()
    val stateDeleteFavorite = MutableLiveData<Boolean>()

    fun getRestaurantFavorite(id: String) {
        viewModelScope.launch {
            repository.getDetailRestaurantFavorite(id).collect {
                _stateDetailRestaurant.value = it
            }
        }
    }

    fun saveFavorite(restaurant: Restaurant) {
        viewModelScope.launch {
            repository.favoriteRestaurant(restaurant.copy(isFavorite = true)).collect {
                stateSaveFavorite.value = it
            }
        }
    }

    fun deleteFavorite(restaurant: Restaurant) {
        viewModelScope.launch {
            repository.deleteRestaurant(restaurant).collect {
                stateDeleteFavorite.value = it
            }
        }
    }

    fun getRestaurant(id: String) {
        viewModelScope.launch {
            repository.getDetailRestaurant(id).collect {
                _stateDetailRestaurant.value = it
            }
        }
    }
}