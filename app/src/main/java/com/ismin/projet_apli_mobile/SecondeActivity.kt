package com.ismin.projet_apli_mobile

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import androidx.appcompat.app.AppCompatActivity


class SecondeActivity : AppCompatActivity() {

    private lateinit var restaurant: Restaurant

    private var isFavori = false

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restaurants.cleverapps.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val restaurantService = retrofit.create(RestaurantService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seconde_activity)

        val nom = intent.getSerializableExtra("EXTRA_RESTAURANT_NOM") as String?


        if (nom != null) {
            val nomrestaurant = nom
            val call = restaurantService.getRestaurantByName(nomrestaurant)
            call.enqueue(object : Callback<Restaurant> {
                override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                    if (response.isSuccessful) {
                        val tempRestaurant = response.body()
                        if (tempRestaurant != null) {
                            restaurant = tempRestaurant
                            displayRestaurantDetails(restaurant)
                        }
                    } else {
                        Toast.makeText(
                            this@SecondeActivity,
                            "Le restaurant n'a pas été trouvé.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                }
            })

            val btnRetour: Button = findViewById(R.id.seconde_activity_btnRetour)

            btnRetour.setOnClickListener {
                returnToMainActivity()
            }

            val btnFavori: ImageButton = findViewById(R.id.btnFavori)
            btnFavori.setOnClickListener { toggleFavori() }
        }
    }

    private fun toggleFavori() {
        isFavori = !isFavori
        updateFavoriButton()
    }

    private fun updateFavoriButton() {
        val btnFavori: ImageButton = findViewById(R.id.btnFavori)

        if (isFavori) {
            btnFavori.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            btnFavori.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    private fun displayRestaurantDetails(restaurant: Restaurant) {
        val nomoffreTextView = findViewById<TextView>(R.id.seconde_activity_nomoffre)
        val typeTextView = findViewById<TextView>(R.id.seconde_activity_type)
        val categorieTextView = findViewById<TextView>(R.id.seconde_activity_categorie)
        val adresseTextView = findViewById<TextView>(R.id.seconde_activity_adresse)
        val commtelTextView = findViewById<TextView>(R.id.seconde_activity_commtel)
        val commwebTextView = findViewById<TextView>(R.id.seconde_activity_commweb)
        val codepostalTextView = findViewById<TextView>(R.id.seconde_activity_codepostal)
        val communeTextView = findViewById<TextView>(R.id.seconde_activity_commune)
        val latitudeTextView = findViewById<TextView>(R.id.seconde_activity_latitude)
        val longitudeTextView = findViewById<TextView>(R.id.seconde_activity_longitude)
        val commmailTextView = findViewById<TextView>(R.id.seconde_activity_commmail)
        val videosurlTextView = findViewById<TextView>(R.id.seconde_activity_videosurl)
        val plateformeurlTextView = findViewById<TextView>(R.id.seconde_activity_plateformeurl)
        val languesParleesTextView = findViewById<TextView>(R.id.seconde_activity_languesparleesacceuil)
        val acceuilGroupeTextView = findViewById<TextView>(R.id.seconde_activity_acceuilgroupe)
        val tarifsTextView = findViewById<TextView>(R.id.seconde_activity_tarifs)
        val modePaiementTextView = findViewById<TextView>(R.id.seconde_activity_modepaiement)

        nomoffreTextView.text = "Nom: ${restaurant.nomoffre}"
        typeTextView.text = "Type: ${restaurant.type ?: "Non spécifié"}"
        categorieTextView.text = "Catégorie: ${restaurant.categorie ?: "Non spécifiée"}"
        adresseTextView.text = "Adresse: ${restaurant.adressecomplete ?: "Non spécifiée"}"
        commtelTextView.text = "Téléphone: ${restaurant.commtel ?: "Non spécifié"}"
        commwebTextView.text = "Site Web: ${restaurant.commweb ?: "Non spécifié"}"
        codepostalTextView.text = "Code postal: ${restaurant.codepostal ?: "Non spécifié"}"
        communeTextView.text = "Commune: ${restaurant.commune ?: "Non spécifiée"}"
        latitudeTextView.text = "Latitude: ${restaurant.latitude ?: "Non spécifiée"}"
        longitudeTextView.text = "Longitude: ${restaurant.longitude ?: "Non spécifiée"}"
        commmailTextView.text = "E-mail: ${restaurant.commmail ?: "Non spécifié"}"
        videosurlTextView.text = "URL des vidéos: ${restaurant.videosurl ?: "Non spécifiée"}"
        plateformeurlTextView.text = "Plateforme URL: ${restaurant.plateformeurl ?: "Non spécifiée"}"
        languesParleesTextView.text = "Langues parlées à l'accueil: ${restaurant.languesparleesacceuil?.joinToString(", ") ?: "Non spécifiées"}"
        acceuilGroupeTextView.text = "Accueil groupe: ${restaurant.acceuilgroupe ?: "Non spécifié"}"
        tarifsTextView.text = "Tarifs: ${restaurant.tarifs ?: "Non spécifiés"}"
        modePaiementTextView.text = "Modes de paiement: ${restaurant.modepaiement?.joinToString(", ") ?: "Non spécifiés"}"

    }

    private fun returnToMainActivity() {

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("EXTRA_RESTAURANT_NOM", restaurant.nomoffre)
        intent.putExtra("EXTRA_RESTAURANT_ISFAVORI", isFavori)
        startActivity(intent)


        finish()
    }

}
