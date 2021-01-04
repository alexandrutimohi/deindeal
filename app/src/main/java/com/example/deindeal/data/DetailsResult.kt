package com.example.deindeal.data

data class DetailsResult(
    val title: String,
    val menu: ArrayList<Menu>
)

data class Menu(
    val title: String,
    val image: String,
    val price: Float
)
