package com.example.testasgn.ui.viewModel

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testasgn.ui.ReminderScheduler
import com.example.testasgn.ui.data.dataTable.MedicalReminder
import com.example.testasgn.ui.data.repository.MedicalReminderRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MedicalReminderViewModel(
    private val repository: MedicalReminderRepo
): ViewModel() {
    private val _reminders = MutableStateFlow<List<MedicalReminder>>(emptyList())

    val reminders: MutableStateFlow<List<MedicalReminder>> = _reminders

    fun loadAllReminders(userIc: String) {
        viewModelScope.launch {
            _reminders.value = repository.getRemindersByUser(userIc)
        }
    }

    fun loadRemindersByDate(userIc: String, date: String) {
        viewModelScope.launch {
            _reminders.value = repository.getRemindersByDate(userIc, date)
        }
    }

    fun addReminder(reminder: MedicalReminder) {
        viewModelScope.launch {
            repository.insertReminder(reminder)
            loadRemindersByDate(reminder.ic, reminder.date)
        }
    }

    fun removeReminder(reminder: MedicalReminder) {
        viewModelScope.launch {
            repository.deleteReminder(reminder)
            loadRemindersByDate(reminder.ic, reminder.date)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleAllUserReminders(context: Context) {
        val currentReminders = _reminders.value
        if (currentReminders.isEmpty()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        currentReminders.forEach { reminder ->
            ReminderScheduler.scheduleReminder(context, reminder)
        }
    }




}