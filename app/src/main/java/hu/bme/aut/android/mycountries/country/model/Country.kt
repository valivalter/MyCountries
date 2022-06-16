package hu.bme.aut.android.mycountries.country.model

data class Country(
    val name: Names,
    val tld: List<String>?,
    val cca2: String,
    val independent: Boolean?,
    val unMember: Boolean?,
    val capital: List<String>?,
    val region: String?,
    val subregion: String?,
    val landlocked: Boolean?,
    val area: Double,
    val maps: Maps,
    val population: Int,
    val car: CarInformation,
    val timezones: List<String>,
    val continents: List<String>,
    val flags: Picture,
    val coatOfArms: Picture,
    val startOfWeek: String?
)

data class Names(
    val common: String,
    val official: String
)

data class Maps(
    val googleMaps: String?,
    val openStreetMaps: String?
)

data class CarInformation(
    val signs: List<String>?,
    val side: String?
)

data class Picture(
    val png: String?
)
