package com.ismin.projet_apli_mobile

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txvNomoffre: TextView = itemView.findViewById(R.id.r_restaurant_txv_nomoffre)
    val txvType: TextView = itemView.findViewById(R.id.r_restaurant_txv_type)
    val txvCategorie: TextView = itemView.findViewById(R.id.r_restaurant_txv_categorie)
    val txvAdresse: TextView = itemView.findViewById(R.id.r_restaurant_txv_adresse)
    val txvCommtel: TextView = itemView.findViewById(R.id.r_restaurant_txv_commtel)
    val txvCommweb: TextView = itemView.findViewById(R.id.r_restaurant_txv_commweb)
    val btnFavori: ImageButton = itemView.findViewById(R.id.r_restaurant_btnFavori)
    val imgRestaurant: ImageView = itemView.findViewById(R.id.imgRestaurant)
}