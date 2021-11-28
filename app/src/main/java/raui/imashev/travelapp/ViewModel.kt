package raui.imashev.travelapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import raui.imashev.travelapp.api.ApiFactory
import raui.imashev.travelapp.database.AppDatabase
import raui.imashev.travelapp.room.Data
import raui.imashev.travelapp.room.Price
import raui.imashev.travelapp.room.Trip
import raui.imashev.travelapp.utils.AirportNameUtil

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val db = AppDatabase.getInstance(application)
    private val counterMistakeValue = 2

    //методы для загрузки данных
    fun loadData() {
        val disposable = ApiFactory.apiService.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                insertData(it)
            }, {
                Log.d("mistake message", it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    private fun insertData(dataList: ArrayList<raui.imashev.travelapp.pojo.Data>) {
        deleteAll()
        val trips = mutableListOf<Trip>()
        val prices = mutableListOf<Price>()
        for (i in dataList) {
            val data = Data(id = dataList.indexOf(i), i.currency)
            db.FlightsDao().insertData(data)
            for (price in i.prices) {
                var counter = 0 //добавил счетчик для невозможности записи дублей
                val priceInsert =
                    Price(dataId = dataList.indexOf(i), amount = price.amount, type = price.type)
                prices.add(priceInsert)
                for (element in prices) {
                    if (element == priceInsert) counter += 1
                }
                if (counter < counterMistakeValue) {
                    db.FlightsDao().insertPrice(priceInsert)
                }
            }
            for (trip in i.trips) {
                var counter = 0
                val from = AirportNameUtil().createName(trip.from)
                val to = AirportNameUtil().createName(trip.to)
                val tripInsert = Trip(dataId = dataList.indexOf(i), from = from, to = to)
                trips.add(tripInsert)
                for (element in trips) {
                    if (element == tripInsert) counter += 1
                }
                if (counter < counterMistakeValue) {
                    db.FlightsDao().insertTrip(tripInsert)
                }
            }
        }
    }


    //Методы для установки RV
    fun getItemsForRV(): List<raui.imashev.travelapp.pojo.Data> {
        return makePricesForRV(getDatum(), getPrices(), getTrips())
    }

    private fun makePricesForRV(
        datum: List<Data>,
        prices: List<Price>,
        trips: List<Trip>
    ): List<raui.imashev.travelapp.pojo.Data> {
        val result = mutableListOf<raui.imashev.travelapp.pojo.Data>()
        for (i in datum) {
            val pricesElements = mutableListOf<raui.imashev.travelapp.pojo.Price>()
            val tripsElements = mutableListOf<raui.imashev.travelapp.pojo.Trip>()
            for (j in prices) {
                if (i.id == j.dataId && j.amount != null && j.type != null) {
                    pricesElements.add(
                        raui.imashev.travelapp.pojo.Price(
                            dataId = i.id,
                            j.amount,
                            j.type
                        )
                    )
                }
            }
            for (g in trips) {
                if (i.id == g.dataId && g.from != null && g.to != null) {
                    tripsElements.add(
                        raui.imashev.travelapp.pojo.Trip(
                            dataId = i.id,
                            g.from,
                            g.to
                        )
                    )
                }
            }
            result.add(
                raui.imashev.travelapp.pojo.Data(
                    i.id,
                    i.currency.toString(),
                    pricesElements,
                    tripsElements
                )
            )
        }
        return result
    }


    //методы для страницы с перелетами
    fun getItemForInfoPage(id: Int): raui.imashev.travelapp.pojo.Data {
        val data = getDataById(id)
        var currency = data.currency
        if (currency == null) {
            currency = ""
        }
        return raui.imashev.travelapp.pojo.Data(
            data.id,
            currency,
            getPricesForInfoPage(getPricesById(id)),
            getTripsForInfoPage(getTripsById(id))
        )
    }

    private fun getPricesForInfoPage(prices: List<Price>): List<raui.imashev.travelapp.pojo.Price> {
        val pricesElements = mutableListOf<raui.imashev.travelapp.pojo.Price>()
        for (i in prices) {
            if (i.amount != null && i.type != null) {
                val priceElement = raui.imashev.travelapp.pojo.Price(i.dataId, i.amount, i.type)
                pricesElements.add(priceElement)

            }
        }
        return pricesElements
    }

    private fun getTripsForInfoPage(trips: List<Trip>): List<raui.imashev.travelapp.pojo.Trip> {
        val tripsElements = mutableListOf<raui.imashev.travelapp.pojo.Trip>()
        for (j in trips) {
            if (j.from != null && j.to != null) {
                val tripElement = raui.imashev.travelapp.pojo.Trip(j.dataId, j.from, j.to)
                tripsElements.add(tripElement)

            }
        }
        return tripsElements
    }


    //БД методы
    private fun getDataById(id: Int): Data {
        return db.FlightsDao().getDataById(id)
    }

    private fun getPricesById(id: Int): List<Price> {
        return db.FlightsDao().getPricesById(id)
    }

    private fun getTripsById(id: Int): List<Trip> {
        return db.FlightsDao().getTripsById(id)
    }

    private fun getPrices(): List<Price> {
        return db.FlightsDao().getPrices()
    }

    private fun getTrips(): List<Trip> {
        return db.FlightsDao().getTrips()
    }

    private fun getDatum(): List<Data> {
        return db.FlightsDao().getDatum()
    }

    private fun deleteAll() {
        db.FlightsDao().deleteAllData()
        db.FlightsDao().deleteAllPrices()
        db.FlightsDao().deleteAllTrips()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}