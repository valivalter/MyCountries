package hu.bme.aut.android.mycountries.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.bme.aut.android.mycountries.country.model.Country
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.concurrent.thread

class CountryInteractor {

    private val countriesApi: CountriesAPI

    init {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(CountriesAPI.ENDPOINT_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val listType = Types.newParameterizedType(List::class.java, Country::class.java)
        val adapter: JsonAdapter<List<Country>> = moshi.adapter(listType)

        this.countriesApi = retrofit.create(CountriesAPI::class.java)
    }

    fun getCountry(
        cca2: String,
        onSuccess: (List<Country>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getCountryRequest = countriesApi.getCountry(cca2)
        runCountryCallsOnBackgroundThread(getCountryRequest, onSuccess, onError)
    }

    private fun runCountryCallsOnBackgroundThread(
        countryCall: Call<List<Country>>,
        onSuccess: (List<Country>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        thread {
            try {
                val countryResponse = countryCall.execute().body()!!
                onSuccess(countryResponse)
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e)
            }
        }
    }
}