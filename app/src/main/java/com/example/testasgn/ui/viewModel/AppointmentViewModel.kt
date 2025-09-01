package com.example.testasgn.ui.viewModel

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testasgn.ui.data.model.Appointment
import com.example.testasgn.ui.data.repository.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.util.Date
import java.util.Locale

class AppointmentViewModel(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    )
    val selectedDate = _selectedDate.asStateFlow()

    fun updateDate(newDate: String) {
        _selectedDate.value = newDate
    }

    fun getAppointments(doctorId: String): StateFlow<List<Appointment>> {
        return _selectedDate.flatMapLatest { date ->
            repository.getAppointments(doctorId, date)
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
}