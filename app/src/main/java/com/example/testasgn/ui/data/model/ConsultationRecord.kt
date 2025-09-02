package com.example.mediconnect.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConsultationRecord(
    @PrimaryKey(autoGenerate = true) val recordId: Int = 0,
    val patientId: String,
    val index: Int,
    val date: String,
    val time: String,
    val chiefComplaint: List<String> = emptyList(),
    val historyOfPresentIllness: List<String> = emptyList(),
    val pastMedicalHistory: List<String> = emptyList(),
    val medicationHistory: List<String> = emptyList(),
    val vitalSigns: List<String> = emptyList(),
    val physicalExamination: List<String> = emptyList(),
    val assessmentDiagnosis: List<String> = emptyList(),
    val investigation: List<String> = emptyList(),
    val planTreatment: List<String> = emptyList(),
    val followUps: List<String> = emptyList()
)