package com.blank.composerestaurant.data

import androidx.lifecycle.LiveData
import com.blank.composerestaurant.data.local.RestaurantDao
import com.blank.composerestaurant.data.model.ResponseListRestaurant
import com.blank.composerestaurant.data.model.Restaurant
import com.blank.composerestaurant.data.remote.RestaurantApi
import com.blank.composerestaurant.ui.common.UiState
import com.blank.composerestaurant.utils.flowState
import com.blank.composerestaurant.utils.flowStateBoolean
import kotlinx.coroutines.flow.Flow

class RepositoryImpl constructor(
    private val service: RestaurantApi, private val dao: RestaurantDao
) : Repository {
    override fun getListRestaurant(): Flow<UiState<ResponseListRestaurant>> = flowState {
        service.getListRestaurant()
    }

    override fun getDetailRestaurant(id: String): Flow<UiState<Restaurant>> = flowState {
        val response = service.getDetailRestaurant(id)
        val favoriteRestaurant = dao.getRestaurant(response.restaurant.id)
        if (favoriteRestaurant == null) {
            return@flowState response.restaurant
        } else {
            return@flowState favoriteRestaurant
        }
    }

    override fun favoriteRestaurant(restaurant: Restaurant): Flow<Boolean> = flowStateBoolean {
        dao.insert(restaurant)
    }

    override fun deleteRestaurant(restaurant: Restaurant): Flow<Boolean> = flowStateBoolean {
        dao.delete(restaurant)
    }

    override fun searchRestaurant(q: String): Flow<UiState<ResponseListRestaurant>> = flowState {
        if (q == "") {
            service.getListRestaurant()
        } else {
            service.searchRestaurant(q)
        }
    }

    override fun getDetailRestaurantFavorite(id: String): Flow<UiState<Restaurant>> = flowState {
        dao.getRestaurant(id)
    }

    override fun getListRestaurantFavorite(): LiveData<List<Restaurant>> = dao.getRestaurants()

}