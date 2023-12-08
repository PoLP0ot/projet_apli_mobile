package com.ismin.projet_apli_mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RestaurantAdapter(private var restaurants: List<Restaurant>, private val itemClickListener: OnItemClickListener ) : RecyclerView.Adapter<RestaurantViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(restaurant: Restaurant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_restaurant, parent, false)
        return RestaurantViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.txvNomoffre.text = restaurant.nomoffre
        holder.txvType.text = restaurant.type
        holder.txvCategorie.text = restaurant.categorie
        holder.txvAdresse.text = restaurant.adressecomplete
        holder.txvCommtel.text = restaurant.commtel
        holder.txvCommweb.text = restaurant.commweb
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(restaurant)
        }
        holder.btnFavori.setImageResource(if (restaurant.isFavori) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)
        Picasso.get().load(restaurant.urlimage).into(holder.imgRestaurant)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    fun updateRestaurants(allRestaurants: List<Restaurant>) {
        restaurants = allRestaurants
        notifyDataSetChanged()
    }
}