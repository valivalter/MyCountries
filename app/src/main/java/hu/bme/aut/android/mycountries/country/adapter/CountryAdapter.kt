package hu.bme.aut.android.mycountries.country.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.mycountries.country.model.Country
import hu.bme.aut.android.mycountries.databinding.RowCountryBinding
import hu.bme.aut.android.mycountries.network.CountriesAPI

class CountryAdapter(private val context: Context) : ListAdapter<Country, CountryAdapter.ViewHolder>(itemCallback) {

    companion object{
        object itemCallback : DiffUtil.ItemCallback<Country>(){
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem == newItem
            }
        }
    }

    var itemClickListener: CountryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = this.getItem(position)

        holder.country = country
        holder.binding.tvCountryName.text = country.name.common
        holder.binding.tvCountryPopulation.text = "Population: ${country.population.toString()}"
        Glide.with(context).load(CountriesAPI.FLAG_URL + country.cca2.lowercase() + ".png").into(holder.binding.ivCountryFlag)

        ViewCompat.setTransitionName(holder.binding.ivCountryFlag, country.flags.png)
    }

    inner class ViewHolder(val binding: RowCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        var country: Country? = null

        init {
            itemView.setOnClickListener { view ->
                country?.let {country -> itemClickListener?.onItemClick(country, binding.ivCountryFlag) }
            }

            itemView.setOnLongClickListener { view ->
                country?.let {country -> itemClickListener?.onItemLongClick(adapterPosition, view, country) }
                true
            }
        }
    }

    interface CountryClickListener {
        fun onItemClick(country: Country, flag: ImageView)
        fun onItemLongClick(position: Int, view: View, country: Country): Boolean
    }
}