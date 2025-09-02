package com.example.mediconnect.data.repository

import com.example.mediconnect.data.dao.PatientDao
import com.example.mediconnect.data.model.Patient

class PatientRepository(private val patientDao: PatientDao) {
    suspend fun insertPatient(patient: Patient) = patientDao.insertPatient(patient)
    suspend fun getPatientById(id: String) = patientDao.getPatientById(id)
    suspend fun getAllPatients() = patientDao.getAllPatients()
    suspend fun deletePatient(patient: Patient) = patientDao.deletePatient(patient)
}