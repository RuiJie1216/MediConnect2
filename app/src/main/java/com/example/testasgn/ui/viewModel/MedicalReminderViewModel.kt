package com.example.testasgn.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testasgn.ui.data.DataTable.MedicalReminder
import com.example.testasgn.ui.data.repository.MedicalReminderRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MedicalReminderViewModel(
    private val repository: MedicalReminderRepo
): ViewModel() {
    private val _reminders = MutableStateFlow<List<MedicalReminder>>(emptyList())

    val reminders: MutableStateFlow<List<MedicalReminder>> = _reminders

    fun loadReminders(userIc: String) {
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

}