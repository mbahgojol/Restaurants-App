package com.blank.composerestaurant.ui.screen.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.composerestaurant.data.Repository
import com.blank.composerestaurant.data.model.ResponseListRestaurant
import com.blank.composerestaurant.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val uistateList = MutableStateFlow<UiState<ResponseListRestaurant>>(UiState.Loading)
    var query by mutableStateOf("")

    fun searchRestaurant(q: String) {
        query = q
        viewModelScope.launch {
            repository.searchRestaurant(query).collect {
                uistateList.value = it
            }
        }
    }

    fun getAllRestaurant() {
        viewModelScope.launch {
            repository.getListRestaurant().collect {
                uistateList.value = it
            }
        }
    }
}