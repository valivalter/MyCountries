package hu.bme.aut.android.mycountries.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "country")
data class RoomCountry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: List<String>,
    val tld: List<String>,
    val cca2: String,
    val independent: String,
    val unMember: String,
    val capital: List<String>,
    val region: String,
    val subregion: String,
    val landlocked: String,
    val area: Double,
    val maps: List<String>,
    val population: Int,
    val carSigns: List<String>,
    val carSide: String,
    val timezones: List<String>,
    val continents: List<String>,
    val flags: String,
    val coatOfArms: String,
    val startOfWeek: String
)

class CountryTypeConverter {

    @TypeConverter
    fun toString(list: MutableList<String>): String {
        var concat = ""
        for (string: String in list) {
            concat += "$string;"
        }
        return concat.dropLast(1)
    }

    @TypeConverter
    fun toMutableList(string: String): MutableList<String> {
        return string.split(';').toMutableList()
    }
}
