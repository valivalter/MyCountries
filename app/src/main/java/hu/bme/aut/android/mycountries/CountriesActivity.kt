package hu.bme.aut.android.mycountries

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.mycountries.country.CountryViewModel
import hu.bme.aut.android.mycountries.country.adapter.CountryAdapter
import hu.bme.aut.android.mycountries.country.model.Country
import hu.bme.aut.android.mycountries.databinding.ActivityCountriesBinding


class CountriesActivity : AppCompatActivity(), CountryAdapter.CountryClickListener {

    private lateinit var binding: ActivityCountriesBinding
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var countryViewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountriesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        countryViewModel.allCountries.observe(this, { countries ->
            countryAdapter.submitList(countries)
        })

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        countryAdapter = CountryAdapter(this)
        countryAdapter.itemClickListener = this
        binding.countriesList.adapter = countryAdapter
    }

    override fun onItemClick(country: Country, flag: ImageView) {
        val intent = Intent(this, CountryDetailsActivity::class.java)
        intent.putExtra(CountryDetailsActivity.KEY_NAME_COMMON, country.name.common)
        intent.putExtra(CountryDetailsActivity.KEY_NAME_FULL, country.name.official)
        intent.putExtra(CountryDetailsActivity.KEY_CCA2, country.cca2)
        intent.putExtra(CountryDetailsActivity.KEY_INDEPENDENT, country.independent.toString())
        intent.putExtra(CountryDetailsActivity.KEY_UN_MEMBER, country.unMember.toString())
        intent.putExtra(CountryDetailsActivity.KEY_REGION, country.region)
        intent.putExtra(CountryDetailsActivity.KEY_SUBREGION, country.subregion)
        intent.putExtra(CountryDetailsActivity.KEY_LANDLOCKED, country.landlocked.toString())
        intent.putExtra(CountryDetailsActivity.KEY_AREA, country.area.toString())
        intent.putExtra(CountryDetailsActivity.KEY_MAP_GOOGLE_MAPS, country.maps.googleMaps)
        intent.putExtra(CountryDetailsActivity.KEY_MAP_OPENSTREETMAP, country.maps.openStreetMaps)
        intent.putExtra(CountryDetailsActivity.KEY_POPULATION, country.population.toString())
        intent.putExtra(CountryDetailsActivity.KEY_CAR_SIDE, country.car.side)
        intent.putExtra(CountryDetailsActivity.KEY_START_OF_WEEK, country.startOfWeek)
        intent.putStringArrayListExtra(CountryDetailsActivity.KEY_TLD, ArrayList(country.tld))
        intent.putStringArrayListExtra(CountryDetailsActivity.KEY_CAPITAL, ArrayList(country.capital))
        intent.putStringArrayListExtra(CountryDetailsActivity.KEY_CAR_SIGNS, ArrayList(country.car.signs))
        intent.putStringArrayListExtra(CountryDetailsActivity.KEY_TIMEZONES, ArrayList(country.timezones))
        intent.putStringArrayListExtra(CountryDetailsActivity.KEY_CONTINENTS, ArrayList(country.continents))

        intent.putExtra(CountryDetailsActivity.KEY_TRANSITION_FLAG, ViewCompat.getTransitionName(flag))
        val options = ActivityOptions.makeSceneTransitionAnimation(this, flag, ViewCompat.getTransitionName(flag))
        startActivity(intent, options.toBundle())
    }

    override fun onItemLongClick(position: Int, view: View, country: Country): Boolean {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu_longclick)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete_country -> {
                    countryViewModel.delete(country)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }
}