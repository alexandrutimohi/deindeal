package com.example.deindeal.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deindeal.databinding.RestaurantDetailsFragmentLayoutBinding
import com.example.deindeal.ui.MainViewModel
import com.example.deindeal.ui.adapters.DetailsGridAdapter
import timber.log.Timber

class RestaurantDetailFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mGridAdapter: DetailsGridAdapter

    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        model.requestRestaurants()

        val binding = RestaurantDetailsFragmentLayoutBinding.inflate(inflater, container, false)
        mRecyclerView = binding.restaurantDetailsRecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(activity,2)

        mGridAdapter = DetailsGridAdapter()
        mRecyclerView.adapter = mGridAdapter

        model.getDetails.observe(viewLifecycleOwner, {
            Timber.d("Got all the menus: $it")
            mGridAdapter.setDetailsMenus(it)
        })

        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getLong("id")?.let {
            model.getMenuForId(it)
        }
        arguments?.getString("title")?.let {
            activity?.title = it
        }
    }
}