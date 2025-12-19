package my.edu.utem.finsafe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Provided: Notification Helper Logic
        // Students just need to trigger this Receiver via AlarmManager
        showNotification(context, "Warning: Low Balance Detected")
    }

    private fun showNotification(context: Context, message: String) {
        // (Boilerplate code to create NotificationChannel and show Notification is pre-written here)
    }
}