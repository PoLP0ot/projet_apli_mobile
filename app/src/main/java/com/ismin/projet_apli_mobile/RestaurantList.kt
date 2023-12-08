package com.ismin.projet_apli_mobile

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class RestaurantList {

    private val storage = HashMap<String, Restaurant>()

    fun addRestaurant(restaurant: Restaurant) {
        storage[restaurant.nomoffre] = restaurant
    }

    fun addRestaurants(restaurants : List<Restaurant>){
        restaurants.forEach{restaurant -> addRestaurant(restaurant)}
    }

    fun getRestaurant(nomoffre: String): Restaurant {
        val restaurant = storage[nomoffre]
        if (restaurant == null) {
            throw IllegalArgumentException("Unknown name")
        }
        return restaurant
    }

    fun getAllRestaurants(): ArrayList<Restaurant> {
        return  ArrayList(storage.values
            .sortedBy { restaurant -> restaurant.nomoffre })
    }

    fun getTypeOfRestaurant(categorie: String): List<Restaurant> {
        return storage.filterValues { restaurant -> restaurant.categorie.equals(categorie) }
            .values
            .sortedBy { restaurant -> restaurant.nomoffre }
    }

    fun getTotalNumberOfRestaurant(): Int {
        return storage.size
    }

    fun clear() {
        storage.clear()
    }

}
