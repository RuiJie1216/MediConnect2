package com.example.mediconnect.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Patient(
    @PrimaryKey val patientId: String,
    val ic: String,
    val name: String,
    val age: String,
    val gender: String,
    val birthOfDate: String,
    val address: String,
    val email: String,
    val phoneNum: String,
    val allergies: String,
    val familyHistory: String
)
