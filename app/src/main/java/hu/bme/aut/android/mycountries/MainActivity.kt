package hu.bme.aut.android.mycountries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.mycountries.country.CountryViewModel
import hu.bme.aut.android.mycountries.country.model.Country
import hu.bme.aut.android.mycountries.databinding.ActivityMainBinding
import hu.bme.aut.android.mycountries.network.CountryInteractor
import hu.bme.aut.android.mycountries.notification.NotificationHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var countryViewModel: CountryViewModel

    private val countryInteractor = CountryInteractor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        binding.btnAddNewCountry.setOnClickListener {
            countryInteractor.getCountry(binding.countryPicker.selectedCountryNameCode, onSuccess = this::addCountry, onError = this::showError)
            Toast.makeText(this, "${binding.countryPicker.selectedCountryName} added", Toast.LENGTH_SHORT).show()
        }
        binding.btnGoToList.setOnClickListener {
            startActivity(Intent(this, CountriesActivity::class.java))
        }

        val pendingIntent = NotificationHelper.createPendingIntentForNotification(this)
        NotificationHelper.scheduleAlarm(this, NotificationHelper.SCHEDULER_DELAY, pendingIntent)
    }

    private fun addCountry(countries: List<Country>) {
        countryViewModel.insert(countries[0])
    }

    private fun showError(e: Throwable) {
        e.printStackTrace()
    }
}
// Alternatíva, hogy egy országot csak egyszer lehessen hozzáadni
/*class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var countryViewModel: CountryViewModel

    private val countryInteractor = CountryInteractor()
    private var excludeString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        setupCountryPicker()

        binding.btnAddNewCountry.setOnClickListener {
            countryInteractor.getCountry(binding.countryPicker.selectedCountryNameCode, onSuccess = this::addCountry, onError = this::showError)
            Toast.makeText(this, "${binding.countryPicker.selectedCountryName} added", Toast.LENGTH_SHORT).show()
        }
        binding.btnGoToList.setOnClickListener {
            startActivity(Intent(this, CountriesActivity::class.java))
        }

        val pendingIntent = NotificationHelper.createPendingIntentForNotification(this)
        NotificationHelper.scheduleAlarm(this, NotificationHelper.SCHEDULER_DELAY, pendingIntent)
    }

    override fun onResume() {
        super.onResume()
        setupCountryPicker()
    }

    private fun setupCountryPicker() {
        val visitedCountries = countryViewModel.getAllCca2s()

        if (visitedCountries.size >= 242) {
            binding.countryPicker.visibility = View.GONE
            binding.btnAddNewCountry.text = getString(R.string.visited_every_country)
            binding.btnAddNewCountry.isEnabled = false
        }
        else {
            binding.countryPicker.visibility = View.VISIBLE
            binding.btnAddNewCountry.text = getString(R.string.add_new_country)
            binding.btnAddNewCountry.isEnabled = true
        }

        excludeString = ""
        for (cca2 in visitedCountries) {
            excludeString += cca2.lowercase() + ","
        }
        binding.countryPicker.setExcludedCountries(excludeString.dropLast(1))

        val notVisitedCountries = CountryApplication.allCountryCca2s.minus(visitedCountries)

        if (notVisitedCountries.isNotEmpty()) {
            binding.countryPicker.setDefaultCountryUsingNameCode(notVisitedCountries[0])
            binding.countryPicker.resetToDefaultCountry()
        }
    }

    private fun addCountry(countries: List<Country>) {
        countryViewModel.insert(countries[0])
        setupCountryPicker()
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private fun showError(e: Throwable) {
        e.printStackTrace()
    }
}*/