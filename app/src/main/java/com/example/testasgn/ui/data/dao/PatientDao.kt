package com.example.mediconnect.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mediconnect.data.model.Patient

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient)

    @Query("SELECT * FROM Patient WHERE patientId = :id")
    suspend fun getPatientById(id: String): Patient?

    @Query("SELECT * FROM Patient")
    suspend fun getAllPatients(): List<Patient>

    @Delete
    suspend fun deletePatient(patient: Patient)
}