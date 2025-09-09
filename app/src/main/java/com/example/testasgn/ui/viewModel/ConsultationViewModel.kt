package com.example.testasgn.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testasgn.ui.data.model.ConsultationRecord
import com.example.testasgn.ui.data.repository.ConsultationRepository
import kotlinx.coroutines.launch

class ConsultationViewModel(
    private val repository: ConsultationRepository
) : ViewModel() {

    fun getRecords(patientId: String) = repository.getRecordsByPatient(patientId)

    fun addRecord(record: ConsultationRecord) {
        viewModelScope.launch { repository.addRecord(record) }
    }
}


