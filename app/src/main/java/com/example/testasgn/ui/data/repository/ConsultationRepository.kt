package com.example.testasgn.ui.data.repository

import com.example.testasgn.ui.data.dao.ConsultationDao
import com.example.testasgn.ui.data.model.ConsultationRecord

class ConsultationRepository(private val consultationDao: ConsultationDao) {
    suspend fun insertRecord(record: ConsultationRecord) = consultationDao.insertRecord(record)
    suspend fun getRecordsByPatient(patientId: String) = consultationDao.getRecordsByPatient(patientId)
    suspend fun deleteRecord(record: ConsultationRecord) = consultationDao.deleteRecord(record)
}