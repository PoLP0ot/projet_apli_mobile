package com.ismin.projet_apli_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val restaurantList = RestaurantList()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://restaurants.cleverapps.io/")
        .build()

    private val restaurantService = retrofit.create(RestaurantService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        restaurantService.getAllRestaurants()
            .enqueue(object : Callback<List<Restaurant>> {
                override fun onResponse(
                    call: Call<List<Restaurant>>,
                    response: Response<List<Restaurant>>
                ) {
                    if (response.isSuccessful) {
                        val allRestaurants: List<Restaurant> = response.body()!!
                        Log.d("Retrofit", "Réponse du serveur: ${response.raw().toString()}")
                        restaurantList.addRestaurants(allRestaurants)

                    }
                    else{
                        Log.e("Retrofit", "Code de réponse: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    Toast.makeText(this@MapActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.e("Retrofit", "Error: ${t.message}", t)
                }
            })

        //instanciation de la map google
        val supportMapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.map_container, supportMapFragment)
            .commit()

        supportMapFragment.getMapAsync(this)

        //configuration du toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_delete -> {
                    true
                }
                R.id.action_refresh -> {
                    true
                }

                R.id.action_list -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.action_map -> {
                    true
                }

                R.id.action_info -> {
                    val intent = Intent(this, InfoActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {


        //ajout de chaque restaurant à la map
        restaurantList.getAllRestaurants().customForEach { restaurant ->
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(restaurant.latitude ?: 0.0, restaurant.longitude ?: 0.0))
                    .title(restaurant.nomoffre)
            )
        }
        val centrePaysDeLaLoire = LatLng(47.4667, -0.8333)

        // Animation de la caméra vers le centre du pays de la Loire
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centrePaysDeLaLoire, 7.0f))
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun <T> List<T>.customForEach(action: (T) -> Unit) {
        for (element in this) {
            action(element)
        }
    }
}
