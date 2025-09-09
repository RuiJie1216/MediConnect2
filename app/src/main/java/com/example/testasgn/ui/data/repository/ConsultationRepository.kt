package com.example.testasgn.ui.data.repository

import com.example.testasgn.ui.data.dao.ConsultationDao
import com.example.testasgn.ui.data.model.ConsultationRecord
import kotlinx.coroutines.flow.Flow

class ConsultationRepository(private val dao: ConsultationDao) {

    fun getRecordsByPatient(patientId: String): Flow<List<ConsultationRecord>> {
        return dao.getRecordsByPatient(patientId)
    }

    suspend fun addRecord(record: ConsultationRecord) {
        dao.insertRecord(record)
    }
}
