package com.blank.composerestaurant.data.remote

import com.blank.composerestaurant.data.model.ResponseDetailRestaurant
import com.blank.composerestaurant.data.model.ResponseListRestaurant
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantApi {
    @GET("list")
    suspend fun getListRestaurant(): ResponseListRestaurant

    @GET("detail/{id}")
    suspend fun getDetailRestaurant(@Path("id") id: String): ResponseDetailRestaurant

    @GET("search")
    suspend fun searchRestaurant(@Query("q") q: String): ResponseListRestaurant
}