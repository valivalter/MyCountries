package hu.bme.aut.android.mycountries.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.mycountries.country.model.*
import hu.bme.aut.android.mycountries.database.CountryDao
import hu.bme.aut.android.mycountries.database.RoomCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val CountryDao: CountryDao) {

    fun getAllCountries(): LiveData<List<Country>> {
        return CountryDao.getAllCountries().map {
                roomCountries -> roomCountries.map { roomCountry -> roomCountry.toDomainModel() }
        }
    }

    fun getAllCca2s(): List<String> {
        return CountryDao.getAllCca2s()
    }

    suspend fun insert(country: Country) = withContext(Dispatchers.IO) {
        CountryDao.insertCountry(country.toRoomModel())
    }

    suspend fun delete(country: Country) = withContext(Dispatchers.IO) {
        val roomCountry = CountryDao.getCountryByCca2(country.cca2) ?: return@withContext
        CountryDao.deleteCountry(roomCountry)
    }

    private fun RoomCountry.toDomainModel(): Country {
        return Country(
            name = Names(name[0], name[1]),
            tld = tld,
            cca2 = cca2,
            independent = independent.toBoolean(),
            unMember = unMember.toBoolean(),
            capital = capital,
            region = region,
            subregion = subregion,
            landlocked = landlocked.toBoolean(),
            area = area,
            maps = Maps(maps[0], maps[1]),
            population = population,
            car = CarInformation(carSigns, carSide),
            timezones = timezones,
            continents = continents,
            flags = Picture(flags),
            coatOfArms = Picture(coatOfArms),
            startOfWeek = startOfWeek
        )
    }

    private fun Country.toRoomModel(): RoomCountry {
        return RoomCountry(
            name = listOf(name.common, name.official),
            tld = tld ?: emptyList(),
            cca2 = cca2,
            independent = independent.toString(),
            unMember = unMember.toString(),
            capital = capital ?: emptyList(),
            region = region ?: "",
            subregion = subregion ?: "",
            landlocked = landlocked.toString(),
            area = area,
            maps = listOf(maps.googleMaps ?: "", maps.openStreetMaps ?: ""),
            population = population,
            carSigns = car.signs ?: emptyList(),
            carSide = car.side ?: "",
            timezones = timezones,
            continents = continents,
            flags = flags.png ?: "",
            coatOfArms = coatOfArms.png ?: "",
            startOfWeek = startOfWeek ?: ""
        )
    }
}