package com.ismin.projet_apli_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.widget.Toolbar
import android.widget.Toast
import com.gu.toolargetool.TooLargeTool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



class MainActivity : AppCompatActivity(), RestaurantCreator {

    private val restaurantList = RestaurantList()

    private val floatingActionButton: FloatingActionButton by lazy {
        findViewById(R.id.a_main_btn_create_restaurant)
    }

    private lateinit var restaurantAdapter: RestaurantAdapter

    companion object {
        const val REQUEST_CODE_UPDATE_RESTAURANT = 1
    }

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://restaurants.cleverapps.io/")
        .client(client)
        .build()

    private val restaurantService = retrofit.create(RestaurantService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {

        TooLargeTool.startLogging(application);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

                        displayRestaurantListFragment()
                    }
                    else{
                        Log.e("Retrofit", "Code de réponse: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.e("Retrofit", "Error: ${t.message}", t)
                }
            })

        floatingActionButton.setOnClickListener {
            displayCreateRestaurantFragment()
        }

        restaurantAdapter = RestaurantAdapter(restaurantList.getAllRestaurants(), object : RestaurantAdapter.OnItemClickListener {
            override fun onItemClick(restaurant: Restaurant) {
                displayRestaurantDetails(restaurant)
            }
        })

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_delete -> {
                    restaurantList.clear()
                    true
                }
                R.id.action_refresh -> {
                    displayRestaurantListFragment()
                    true
                }

                R.id.action_list -> {
                    true
                }
                R.id.action_map -> {
                    val intent = Intent(this, MapActivity::class.java)
                    //intent.putExtra("EXTRA_RESTAURANT_LIST", restaurantList.getAllRestaurants())
                    startActivity(intent)
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

    private fun displayRestaurantDetails(restaurant: Restaurant) {
        val intent = Intent(this, SecondeActivity::class.java)
        intent.putExtra("EXTRA_RESTAURANT_NOM", restaurant.nomoffre)
        startActivityForResult(intent, REQUEST_CODE_UPDATE_RESTAURANT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_UPDATE_RESTAURANT && resultCode == RESULT_OK && data != null) {
            val nomRestaurant = data.getStringExtra("EXTRA_RESTAURANT_NOM")
            if (nomRestaurant != null) {
                val nomOffre = nomRestaurant
                val isFavori = data.getBooleanExtra("EXTRA_RESTAURANT_ISFAVORI", false)

                restaurantList.getRestaurant(nomOffre).isFavori = isFavori

                displayRestaurantListFragment()
            }
        }
    }


    private fun displayRestaurantListFragment() {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(
        R.id.a_main_lyt_fragment,
        RestaurantListFragment.newInstance(restaurantList.getAllRestaurants())
    )
    transaction.commit()
    floatingActionButton.visibility = View.VISIBLE
}

    private fun displayCreateRestaurantFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.a_main_lyt_fragment,
            CreateRestaurantFragment()
        )
        transaction.commit()
        floatingActionButton.visibility = View.GONE
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onRestaurantCreated(restaurant: Restaurant) {
        restaurantService.addRestaurant(restaurant)
            .enqueue {
                onResponse = {
                    val restaurantFromServer: Restaurant? = it.body()
                    restaurantList.addRestaurant(restaurantFromServer!!)
                    displayRestaurantListFragment()
                }

                onFailure = {
                    Toast.makeText(this@MainActivity, it?.message, Toast.LENGTH_SHORT).show()
                }
            }

        restaurantList.addRestaurant(restaurant)
        displayRestaurantListFragment()
    }
}
