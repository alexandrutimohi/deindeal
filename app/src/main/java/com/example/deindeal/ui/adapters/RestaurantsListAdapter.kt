package com.example.deindeal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deindeal.App
import com.example.deindeal.R
import com.example.deindeal.data.Restaurant

class RestaurantsListAdapter :
    RecyclerView.Adapter<RestaurantsListAdapter.RestaurantsViewHolder>() {

    private var mRestaurantsList = ArrayList<Restaurant>()

    fun setRestaurantsList(list: ArrayList<Restaurant>) {
        mRestaurantsList = list
        notifyDataSetChanged()
    }

    inner class RestaurantsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView? = null
        var subtitle: TextView? = null
        var image: ImageView? = null
        var id: Long = 0
        var container: ConstraintLayout? = null

        init {
            title = itemView.findViewById(R.id.restaurant_title_text)
            image = itemView.findViewById(R.id.restaurant_image_view)
            subtitle = itemView.findViewById(R.id.restaurant_subtitle_text)
            container = itemView.findViewById(R.id.view_container)

            itemView.setOnClickListener {
                val bundle = bundleOf("id" to id, "title" to title!!.text)

                itemView.findNavController().navigate(R.id.action_restaurants_to_detail, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false)

        return RestaurantsViewHolder(inflater)

    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        val currentItem = mRestaurantsList[position]

        val imageUrl = currentItem.image
        val title = currentItem.title
        val subtitle = currentItem.subtitle

        holder.id = currentItem.id
        holder.image?.let { Glide.with(App.appContext).load(imageUrl).into(it) }

        holder.subtitle?.text = subtitle
        holder.title?.text = title
    }

    override fun getItemCount(): Int {
        return mRestaurantsList.size
    }
}