package hu.bme.aut.android.mycountries.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomCountry::class]
)
@TypeConverters(
    CountryTypeConverter::class
)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun CountryDao(): CountryDao
}