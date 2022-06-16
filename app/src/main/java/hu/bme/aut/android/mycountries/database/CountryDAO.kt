package hu.bme.aut.android.mycountries.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CountryDao {

    @Insert
    fun insertCountry(country: RoomCountry)

    @Query("SELECT * FROM country")
    fun getAllCountries(): LiveData<List<RoomCountry>>

    @Query("SELECT cca2 FROM country")
    fun getAllCca2s(): List<String>

    @Update
    fun updateCountry(country: RoomCountry): Int

    @Delete
    fun deleteCountry(country: RoomCountry)

    @Query("SELECT * FROM country WHERE cca2 == :cca2")
    fun getCountryByCca2(cca2: String?): RoomCountry?
}