package com.example.deindeal.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deindeal.App
import com.example.deindeal.data.Filter
import com.example.deindeal.data.Menu
import com.example.deindeal.data.Restaurant
import com.example.deindeal.network.INetworkService
import com.example.deindeal.network.NetworkApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.IoScheduler
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val _details = MutableLiveData<ArrayList<Menu>>()

    private val _grandResult = MutableLiveData<Result>()

    private val compositeDisposable = CompositeDisposable()

    public val getDetails: LiveData<ArrayList<Menu>>
        get() = _details

    public val getResults: LiveData<Result>
        get() = _grandResult


    fun requestRestaurants() {

        val request = App.networkConnection.doGetRestaurants()

        compositeDisposable.add(
            request.subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        _grandResult.postValue(
                            Result.Restaurants(
                                result.restaurants,
                                result.filters
                            )
                        )
                    }, {
                        _grandResult.postValue(Result.Failure(it.message))
                        Timber.e("We didn't get the Restaurants: ${it.message}")
                    }
                )

        )
    }

    fun getMenuForId(id: Long) {
        val request = App.networkConnection.doGetDetails(id)

        compositeDisposable.add(
            request.subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        _details.postValue(result.menu)
                    }, {
                        Timber.e("We didn't get the details: ${it.message}")
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}

sealed class Result {
    data class Failure(var errorMsg: String?) : Result()
    data class Restaurants(var restaurants: List<Restaurant>, var categories: List<Filter>) :
        Result()

    data class MenuDetails(var menus: List<Menu>) : Result()
}