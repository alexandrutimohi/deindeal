package com.example.deindeal.network

import com.example.deindeal.data.DetailsResult
import com.example.deindeal.data.RestaurantsResults
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface INetworkService {

    @GET("{id}/restaurant.json")
    fun doGetDetails(@Path("id") id: Long): Observable<DetailsResult>

    @GET("restaurants.json")
    fun doGetRestaurants(): Observable<RestaurantsResults>

}