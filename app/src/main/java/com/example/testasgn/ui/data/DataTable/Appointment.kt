package com.example.testasgn.ui.data.DataTable

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Appointment(
    @PrimaryKey val appointmentId: String,
    val doctorId: String,
    val patientId: String,
    val name: String,
    val date: String,
    val time: String,
    val detail: String,
    val chiefComplaints: List<String> = emptyList(),
    val hpi: List<String> = emptyList(),
    val medicationHistory: List<String> = emptyList(),
    val followUps: List<String> = emptyList(),
    val status: String = "Booked"
)