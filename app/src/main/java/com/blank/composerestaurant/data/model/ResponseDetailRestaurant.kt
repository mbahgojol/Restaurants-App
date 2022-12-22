package com.blank.composerestaurant.data.model


import com.google.gson.annotations.SerializedName

data class ResponseDetailRestaurant(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("restaurant") val restaurant: Restaurant
)