package com.example.deindeal.data

data class RestaurantsResults(
        val restaurants: ArrayList<Restaurant>,
        val filters: ArrayList<Filter>
)

data class Restaurant(
        val id: Long,
        val title: String,
        val subtitle: String,
        val image: String,
        val filters: ArrayList<String>
)

data class Filter(
        val id: String,
        val label: String,
        val icon: String
)
