package com.blank.composerestaurant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blank.composerestaurant.data.model.Restaurant

@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun daoResto(): RestaurantDao
}