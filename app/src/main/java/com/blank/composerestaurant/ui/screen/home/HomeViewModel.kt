package com.blank.composerestaurant.ui.screen.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.composerestaurant.data.Repository
import com.blank.composerestaurant.data.model.ResponseListRestaurant
import com.blank.composerestaurant.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val uistateList = MutableLiveData<UiState<ResponseListRestaurant>>()

    fun getAllRestaurant() {
        viewModelScope.launch {
            repository.getListRestaurant().collect {
                uistateList.value = it
            }
        }
    }
}