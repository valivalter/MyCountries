package hu.bme.aut.android.mycountries

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import hu.bme.aut.android.mycountries.databinding.ActivityCountryDetailsBinding
import hu.bme.aut.android.mycountries.network.CountriesAPI

class CountryDetailsActivity : AppCompatActivity() {

    companion object {
        const val KEY_NAME_COMMON = "KEY_NAME_COMMON"
        const val KEY_NAME_FULL = "KEY_NAME_FULL"
        const val KEY_TLD = "KEY_TLD"
        const val KEY_CCA2 = "KEY_CCA2"
        const val KEY_INDEPENDENT = "KEY_INDEPENDENT"
        const val KEY_UN_MEMBER = "KEY_UN_MEMBER"
        const val KEY_CAPITAL = "KEY_CAPITAL"
        const val KEY_REGION = "KEY_REGION"
        const val KEY_SUBREGION = "KEY_SUBREGION"
        const val KEY_LANDLOCKED = "KEY_LANDLOCKED"
        const val KEY_AREA = "KEY_AREA"
        const val KEY_MAP_GOOGLE_MAPS = "KEY_MAP_GOOGLE_MAPS"
        const val KEY_MAP_OPENSTREETMAP = "KEY_MAP_OPENSTREETMAP"
        const val KEY_POPULATION = "KEY_POPULATION"
        const val KEY_CAR_SIGNS = "KEY_CAR_SIGNS"
        const val KEY_CAR_SIDE = "KEY_CAR_SIDE"
        const val KEY_TIMEZONES = "KEY_TIMEZONES"
        const val KEY_CONTINENTS = "KEY_CONTINENTS"
        const val KEY_START_OF_WEEK = "KEY_START_OF_WEEK"

        const val KEY_TRANSITION_FLAG = "KEY_TRANSITION_FLAG"
    }

    private lateinit var binding: ActivityCountryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val cca2 = intent.getStringExtra(CountryDetailsActivity.KEY_CCA2)?.lowercase()
        Glide.with(this).load(CountriesAPI.FLAG_URL + cca2 + ".png").into(binding.ivFlag)
        binding.ivFlag.transitionName = intent.getStringExtra(CountryDetailsActivity.KEY_TRANSITION_FLAG)
        Glide.with(this).load(CountriesAPI.COAT_OF_ARMS_URL + cca2 + ".png").into(binding.ivCoatOfArms)

        binding.tvCommonName.text = intent.getStringExtra(CountryDetailsActivity.KEY_NAME_COMMON)
        binding.tvFullName.text = intent.getStringExtra(CountryDetailsActivity.KEY_NAME_FULL)
        binding.tvIndependent.text = intent.getStringExtra(CountryDetailsActivity.KEY_INDEPENDENT)
        binding.tvUnMember.text = intent.getStringExtra(CountryDetailsActivity.KEY_UN_MEMBER)
        binding.tvRegion.text = intent.getStringExtra(CountryDetailsActivity.KEY_REGION)
        binding.tvSubregion.text = intent.getStringExtra(CountryDetailsActivity.KEY_SUBREGION)
        binding.tvLandlocked.text = intent.getStringExtra(CountryDetailsActivity.KEY_LANDLOCKED)
        binding.tvArea.text = intent.getStringExtra(CountryDetailsActivity.KEY_AREA) + " km²"
        binding.tvPopulation.text = intent.getStringExtra(CountryDetailsActivity.KEY_POPULATION)
        binding.tvCarSide.text = intent.getStringExtra(CountryDetailsActivity.KEY_CAR_SIDE)
        binding.tvStartOfWeek.text = intent.getStringExtra(CountryDetailsActivity.KEY_START_OF_WEEK)

        binding.tvTld.text = intent.getStringArrayListExtra(CountryDetailsActivity.KEY_TLD).toString().drop(1).dropLast(1)
        binding.tvCapital.text = intent.getStringArrayListExtra(CountryDetailsActivity.KEY_CAPITAL).toString().drop(1).dropLast(1)
        binding.tvCarSigns.text = intent.getStringArrayListExtra(CountryDetailsActivity.KEY_CAR_SIGNS).toString().drop(1).dropLast(1)
        binding.tvTimezones.text = intent.getStringArrayListExtra(CountryDetailsActivity.KEY_TIMEZONES).toString().drop(1).dropLast(1)
        binding.tvContinents.text = intent.getStringArrayListExtra(CountryDetailsActivity.KEY_CONTINENTS).toString().drop(1).dropLast(1)


        binding.btnGoogleMaps.setOnClickListener {
            val googleMapsUri = Uri.parse(intent.getStringExtra(CountryDetailsActivity.KEY_MAP_GOOGLE_MAPS))
            val googleMapsIntent = Intent(Intent.ACTION_VIEW, googleMapsUri)
            googleMapsIntent.setPackage("com.google.android.apps.maps")
            startActivity(googleMapsIntent)
        }
        binding.btnOpenStreetMap.setOnClickListener {
            val openStreetMapUri = Uri.parse(intent.getStringExtra(CountryDetailsActivity.KEY_MAP_OPENSTREETMAP))
            val openStreetMapIntent = Intent(Intent.ACTION_VIEW, openStreetMapUri)
            startActivity(openStreetMapIntent)
        }
    }
}