package com.example.testasgn.ui

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.example.testasgn.ui.data.dataTable.MedicalReminder
import com.example.testasgn.ui.userTheme.ReminderReceiver
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object ReminderScheduler {

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleReminder(context: Context, reminder: MedicalReminder) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", "Time to take ${reminder.name}")
            putExtra("message", "Dosage: ${reminder.dose}, Instruction: ${reminder.instruction}")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val dateTime = LocalDateTime.parse(
            "${reminder.date} ${reminder.time}",
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a", Locale.getDefault())
        )

        val millis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        try {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, millis, pendingIntent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}
