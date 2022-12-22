package com.blank.composerestaurant.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.blank.composerestaurant.data.model.Restaurant

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(restaurant: Restaurant)

    @Delete
    fun delete(restaurant: Restaurant)

    @Query("select * from restaurant where id = :id limit 1")
    suspend fun getRestaurant(id: String): Restaurant

    @Query("select * from restaurant")
    fun getRestaurants(): LiveData<List<Restaurant>>
}