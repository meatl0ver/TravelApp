package raui.imashev.travelapp.database

import androidx.room.*
import raui.imashev.travelapp.room.Data
import raui.imashev.travelapp.room.Price
import raui.imashev.travelapp.room.Trip

@Dao
interface FlightsDao {
    //Методы для работы с Data
    @Query("SELECT * FROM full_data ORDER BY currency DESC")
    fun getDatum(): List<Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: Data)

    @Query("DELETE FROM full_data")
    fun deleteAllData(): Int

    @Query("SELECT * FROM full_data WHERE id = :selectedID")
    fun getDataById(selectedID: Int): Data


    //Методы для работы с Price
    @Query("SELECT * FROM price")
    fun getPrices(): List<Price>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrice(price: Price)

    @Query("DELETE FROM price")
    fun deleteAllPrices(): Int

    @Query("SELECT * FROM price WHERE dataId = :selectedID")
    fun getPricesById(selectedID: Int): List<Price>


    //Методы для работы с Trip
    @Query("SELECT * FROM trip")
    fun getTrips(): List<Trip>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrip(trip: Trip)

    @Query("DELETE FROM trip")
    fun deleteAllTrips(): Int

    @Query("SELECT * FROM trip WHERE dataId = :selectedID")
    fun getTripsById(selectedID: Int): List<Trip>
}