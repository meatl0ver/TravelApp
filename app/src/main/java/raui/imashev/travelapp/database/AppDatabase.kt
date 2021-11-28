package raui.imashev.travelapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import raui.imashev.travelapp.room.Data
import raui.imashev.travelapp.room.Price
import raui.imashev.travelapp.room.Trip

@Database(entities = [Data::class, Price::class, Trip::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    ).allowMainThreadQueries()
                        .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun FlightsDao(): FlightsDao

}