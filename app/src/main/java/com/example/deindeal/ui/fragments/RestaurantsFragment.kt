package com.example.deindeal.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deindeal.data.Restaurant
import com.example.deindeal.databinding.RestaurantsFragmentLayoutBinding
import com.example.deindeal.ui.MainViewModel
import com.example.deindeal.ui.Result
import com.example.deindeal.ui.adapters.FilterAdapter
import com.example.deindeal.ui.adapters.FilterAdapter.ClickListener
import com.example.deindeal.ui.adapters.RestaurantsListAdapter
import timber.log.Timber
import java.util.*

class RestaurantsFragment : Fragment() {
    private val model: MainViewModel by activityViewModels()
    private lateinit var mRestaurantsRecyclerView: RecyclerView
    private lateinit var mFilterRecyclerView: RecyclerView
    private lateinit var mRestaurantsListAdapter: RestaurantsListAdapter
    private lateinit var mFilterAdapter: FilterAdapter

    private var mLabelSelected = ""

    private var mRestaurantList = emptyList<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        model.requestRestaurants()

        val binding = RestaurantsFragmentLayoutBinding.inflate(inflater, container, false)
        mRestaurantsRecyclerView = binding.restaurantsRecyclerView
        mRestaurantsRecyclerView.setHasFixedSize(true)
        mRestaurantsRecyclerView.layoutManager = LinearLayoutManager(activity)

        mRestaurantsListAdapter = RestaurantsListAdapter()
        mRestaurantsRecyclerView.adapter = mRestaurantsListAdapter

        //////////////////////////////////////////////
        mFilterRecyclerView = binding.filterRestaurantsRv
        mFilterRecyclerView.setHasFixedSize(true)
        mFilterRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        mFilterAdapter = FilterAdapter()
        mFilterAdapter.setOnItemClickListener(object : ClickListener {
            override fun onItemClick(label: String) {

                if (label == mLabelSelected) {
                    mRestaurantsListAdapter.setRestaurantsList(mRestaurantList)
                } else {
                    val sortedRList =
                        mRestaurantList.filter { it.filters.contains(label.toLowerCase(Locale.ROOT)) }

                    Timber.d("Sorted: $sortedRList")
                    mRestaurantsListAdapter.setRestaurantsList(sortedRList)
                    mLabelSelected = label
                }
            }
        })
        mFilterRecyclerView.adapter = mFilterAdapter

        //////////////////////////////////////////////

        val swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            model.requestRestaurants()
            swipeRefreshLayout.isRefreshing = false
        }

        model.getResults.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Failure -> Toast.makeText(activity, it.errorMsg, Toast.LENGTH_SHORT)
                    .show()
                is Result.Restaurants -> {
                    mRestaurantList = it.restaurants
                    mRestaurantsListAdapter.setRestaurantsList(mRestaurantList)
                    mFilterAdapter.setCategories(it.categories)
                }
                else -> {
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Restaurants"
    }
}