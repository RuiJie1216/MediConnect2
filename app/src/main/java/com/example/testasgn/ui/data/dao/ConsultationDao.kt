package com.example.mediconnect.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mediconnect.data.model.ConsultationRecord

@Dao
interface ConsultationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: ConsultationRecord)

    @Query("SELECT * FROM ConsultationRecord WHERE patientId = :patientId")
    suspend fun getRecordsByPatient(patientId: String): List<ConsultationRecord>

    @Delete
    suspend fun deleteRecord(record: ConsultationRecord)
}