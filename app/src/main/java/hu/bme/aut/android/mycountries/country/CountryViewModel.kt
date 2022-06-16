package hu.bme.aut.android.mycountries.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.mycountries.country.model.Country
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {

    private val repository: Repository
    val allCountries: LiveData<List<Country>>

    init {
        val countryDao = CountryApplication.countryDatabase.CountryDao()
        repository = Repository(countryDao)
        allCountries = repository.getAllCountries()
    }

    fun insert(country: Country) = viewModelScope.launch {
        repository.insert(country)
    }

    fun delete(country: Country) = viewModelScope.launch {
        repository.delete(country)
    }

    fun getAllCca2s(): List<String> {
        return repository.getAllCca2s()
    }
}