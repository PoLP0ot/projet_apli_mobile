package com.ismin.projet_apli_mobile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ismin.projet_apli_mobile.MainActivity.Companion.REQUEST_CODE_UPDATE_RESTAURANT

private const val ARG_RESTAURANTS = "param1"

class RestaurantListFragment : Fragment() {
    private var restaurants: ArrayList<Restaurant> = arrayListOf()
    private lateinit var restaurantAdapter: RestaurantAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            restaurants = it.getSerializable(ARG_RESTAURANTS) as ArrayList<Restaurant>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        recyclerView = view.findViewById(R.id.f_restaurant_list_rcv)
        restaurantAdapter = RestaurantAdapter(restaurants, object : RestaurantAdapter.OnItemClickListener{
            override fun onItemClick(restaurant: Restaurant) {
                displayRestaurantDetails(restaurant)
            }
        })
        recyclerView.adapter = restaurantAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        return view
    }

    private fun displayRestaurantDetails(restaurant: Restaurant) {
        val intent = Intent(requireContext(), SecondeActivity::class.java)
        intent.putExtra("EXTRA_RESTAURANT_NOM", restaurant.nomoffre)
        startActivityForResult(intent, REQUEST_CODE_UPDATE_RESTAURANT)
    }

    companion object {
        @JvmStatic
        fun newInstance(restaurants: ArrayList<Restaurant>) =
            RestaurantListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_RESTAURANTS, restaurants)
                }
            }
    }
}
