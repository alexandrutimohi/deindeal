package com.example.deindeal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
//import coil.api.load
import com.bumptech.glide.Glide
import com.example.deindeal.App
import com.example.deindeal.R
import com.example.deindeal.data.Menu

class DetailsGridAdapter :
    RecyclerView.Adapter<DetailsGridAdapter.DetailHolder>() {

    private var elements: ArrayList<Menu> = arrayListOf()

    fun setDetailsMenus(list: ArrayList<Menu>) {
        elements = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DetailHolder {
        val itemLayout: Int = R.layout.rv_grid_item

        val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        return DetailHolder(view)
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        val currentItem = elements[position]

        val imageUrl = currentItem.image
        val title = currentItem.title
        val price = currentItem.price

        holder.detailImage?.let { Glide.with(App.appContext).load(imageUrl).into(it) }

        holder.detailPrice?.text = price.toString()
        holder.detailTitle?.text = title

    }

    inner class DetailHolder(view: View) : RecyclerView.ViewHolder(view) {
        var detailTitle: TextView? = null
        var detailPrice: TextView? = null
        var detailImage: ImageView? = null
        var id: Long = 0
        var container: ConstraintLayout? = null

        init {
            detailTitle = itemView.findViewById(R.id.tvTitle)
            detailImage = itemView.findViewById(R.id.ivCover)
            detailPrice = itemView.findViewById(R.id.tvPrice)
            container = itemView.findViewById(R.id.lMainItemHolder)

        }
    }

}