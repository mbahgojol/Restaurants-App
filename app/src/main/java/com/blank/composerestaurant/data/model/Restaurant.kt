package com.blank.composerestaurant.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Restaurant")
data class Restaurant(
    @ColumnInfo(name = "city") @SerializedName("city") val city: String = "",
    @ColumnInfo(name = "description") @SerializedName("description") val description: String = "",
    @PrimaryKey @ColumnInfo(name = "id") @SerializedName("id") val id: String = "",
    @ColumnInfo(name = "name") @SerializedName("name") val name: String = "",
    @ColumnInfo(name = "pictureId") @SerializedName("pictureId") val pictureId: String = "",
    @ColumnInfo(name = "rating") @SerializedName("rating") val rating: Double = 0.0,
    @ColumnInfo(name = "isfavorite") val isFavorite: Boolean = false
) {
    val largeImg
        get() = "https://restaurant-api.dicoding.dev/images/large/$pictureId"

    val smallImg
        get() = "https://restaurant-api.dicoding.dev/images/small/$pictureId"
}