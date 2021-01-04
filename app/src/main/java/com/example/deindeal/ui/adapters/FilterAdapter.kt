package com.example.deindeal.ui.adapters

import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deindeal.App
import com.example.deindeal.R
import com.example.deindeal.data.Filter
import com.example.deindeal.SVG.SvgSoftwareLayerSetter


class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    private var mCategories = ArrayList<Filter>()

    private var mClickListener: ClickListener? = null

    private var requestBuilder: RequestBuilder<PictureDrawable>? = null

    fun setCategories(list: ArrayList<Filter>) {
        mCategories = list
        notifyDataSetChanged()
    }

    inner class FilterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var title: TextView? = null
        var image: ImageView? = null
        var id = ""
        var container: ConstraintLayout? = null

        init {
            title = itemView.findViewById(R.id.filter_text_view)
            image = itemView.findViewById(R.id.filter_image)
            container = itemView.findViewById(R.id.filter_container)

            itemView.setOnClickListener {
                mClickListener?.onItemClick(id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterAdapter.FilterViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)

        return FilterViewHolder(inflater)
    }

    override fun onBindViewHolder(
        holder: FilterAdapter.FilterViewHolder,
        position: Int
    ) {
        val currentItem = mCategories[position]

        val imageUrl = currentItem.icon
        val title = currentItem.label


        requestBuilder = Glide.with(App.appContext)
            .`as`(PictureDrawable::class.java)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(SvgSoftwareLayerSetter())

        requestBuilder!!.load(imageUrl).into(holder.image!!)
        holder.title?.text = title
        holder.id = currentItem.id
    }

    override fun getItemCount(): Int {
        return mCategories.size
    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(label: String)
    }
}