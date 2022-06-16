package hu.bme.aut.android.mycountries.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import hu.bme.aut.android.mycountries.MainActivity
import hu.bme.aut.android.mycountries.R
import hu.bme.aut.android.mycountries.country.CountryApplication
import hu.bme.aut.android.mycountries.country.Repository
import hu.bme.aut.android.mycountries.network.CountryInteractor
import kotlin.random.Random

class NotificationHelper {
    companion object {

        private const val CHANNEL_ID = "hu.bme.aut.android.mycountries.visitcountry"
        const val SCHEDULER_DELAY: Long = (14 * 24 * 60 * 60 * 1000).toLong() // Two weeks in milliseconds

        fun createNotificationChannel(ctx: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Visit country"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                with(NotificationManagerCompat.from(ctx)) {
                    createNotificationChannel(channel)
                }
            }
        }

        fun createPendingIntentForNotification(ctx: Context): PendingIntent {
            val intent = Intent(ctx, NotificationBroadcastReceiver::class.java)
            return PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }


        fun createTimeToTravelNotification(ctx: Context) {
            val countryDao = CountryApplication.countryDatabase.CountryDao()
            val repository = Repository(countryDao)
            val visitedCountries = repository.getAllCca2s()
            val notVisitedCountries = CountryApplication.allCountryCca2s.minus(visitedCountries)

            if (notVisitedCountries.isNotEmpty()) {
                CountryInteractor().getCountry(
                    notVisitedCountries[Random.nextInt(notVisitedCountries.size)],
                    onSuccess = { countries -> sendNotification(ctx, countries[0].name.common) },
                    onError = this::showError
                )
            }
        }

        private fun sendNotification(ctx: Context, countryName: String) {
            val intent = Intent(ctx, MainActivity::class.java)
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val desc = "You have never visited $countryName!"

            val builder =
                NotificationCompat.Builder(ctx, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Time to travel!")
                    .setContentText(desc)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            with(NotificationManagerCompat.from(ctx)) {
                notify(Random.Default.nextInt(10000, 100000), builder.build())
            }
        }

        private fun showError(e: Throwable) {
            e.printStackTrace()
        }

        fun scheduleAlarm(ctx: Context, delayMilliseconds : Long, pendingIntent: PendingIntent) {
            val triggerDateTime = System.currentTimeMillis() + delayMilliseconds
            val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            alarmManager?.set(AlarmManager.RTC_WAKEUP, triggerDateTime, pendingIntent)
        }
    }
}