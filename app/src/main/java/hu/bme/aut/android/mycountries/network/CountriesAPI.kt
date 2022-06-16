package hu.bme.aut.android.mycountries.network

import hu.bme.aut.android.mycountries.country.model.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesAPI {

    companion object {
        const val ENDPOINT_URL = "https://restcountries.com/v3.1/alpha/"
        const val FLAG_URL = "https://flagcdn.com/w320/"
        const val COAT_OF_ARMS_URL = "https://mainfacts.com/media/images/coats_of_arms/"
    }

    @GET("{cca2}")
    fun getCountry(@Path("cca2") cca2: String): Call<List<Country>>
}