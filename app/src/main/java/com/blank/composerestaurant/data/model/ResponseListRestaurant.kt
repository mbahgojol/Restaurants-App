package com.blank.composerestaurant.data.model


import com.google.gson.annotations.SerializedName

data class ResponseListRestaurant(
    @SerializedName("count") val count: Int,
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("restaurants") val restaurants: List<Restaurant>
)