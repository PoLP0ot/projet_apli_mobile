package com.ismin.projet_apli_mobile


// InfoActivity.kt

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val infoText = """
            Informations générales sur l'application :
            - URL des données : https://data.opendatasoft.com/explore/dataset/234400034_070-008_offre-touristique-restaurants-rpdl%40paysdelaloire/information/?location=8,47.69649,-0.86565&basemap=jawg.streets
            - Les données affichées sont des informations sur les différents restaurants en Pays de la Loire, on peut retrouver notamment le nom, le type de restaurant, son adresse, numéro de téléphone ...
        """.trimIndent()
        val txvInfoAppli: TextView = findViewById<TextView>(R.id.i_textViewInfo)
        txvInfoAppli.text = infoText

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
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
