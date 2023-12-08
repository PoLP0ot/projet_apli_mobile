package com.ismin.projet_apli_mobile

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface RestaurantService {

    @GET("restaurant")
    fun getAllRestaurants(): Call<List<Restaurant>>
    @POST("restaurant")
    fun addRestaurant(@Body restaurant: Restaurant): Call<Restaurant>
    @GET("restaurant")
    fun getRestaurantByName(@Query("nomoffre") nomoffre: String): Call<Restaurant>


}