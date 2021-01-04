package com.example.deindeal.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deindeal.data.Filter
import com.example.deindeal.data.Menu
import com.example.deindeal.data.Restaurant
import com.example.deindeal.network.INetworkConnection
import com.example.deindeal.network.NetworkApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.IoScheduler
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val _restaurants = MutableLiveData<ArrayList<Restaurant>>()
    private val _categories = MutableLiveData<ArrayList<Filter>>()
    private val _details = MutableLiveData<ArrayList<Menu>>()

    private val compositeDisposable = CompositeDisposable()

    public val getRestaurants: LiveData<ArrayList<Restaurant>>
        get() = _restaurants

    public val getDetails: LiveData<ArrayList<Menu>>
        get() = _details

    public val getFilters: LiveData<ArrayList<Filter>>
        get() = _categories


    fun requestRestaurants() {

        val getContentService: INetworkConnection = NetworkApi.contentApi()
        val request = getContentService.doGetRestaurants()

        compositeDisposable.add(
            request.subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        _restaurants.postValue(result.restaurants)
                        _categories.postValue(result.filters)
                    }, {
                        Timber.e("We didn't get the Restaurants: ${it.message}")
                    }
                )
        )
    }

    fun getMenuForId(id: Long) {
        val getContentService: INetworkConnection = NetworkApi.contentApi()
        val request = getContentService.doGetDetails(id)

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