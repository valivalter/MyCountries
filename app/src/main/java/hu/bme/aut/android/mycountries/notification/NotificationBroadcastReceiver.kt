package hu.bme.aut.android.mycountries.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.createTimeToTravelNotification(context.applicationContext)

        val pendingIntent = NotificationHelper.createPendingIntentForNotification(context.applicationContext)
        NotificationHelper.scheduleAlarm(context.applicationContext, NotificationHelper.SCHEDULER_DELAY, pendingIntent)
    }
}