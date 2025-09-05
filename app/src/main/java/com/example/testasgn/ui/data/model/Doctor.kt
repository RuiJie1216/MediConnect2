package com.example.testasgn.ui.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Doctor(
    @PrimaryKey val doctorId: String = "",
    val loginId: String,
    val docName: String = "",
    val docDegree: String = "",
    val docSpecialty: String = "",
    val yearOfPractice: Int = 0,
    val language: String = "",
    val dayOff: String = "",
    val quote: String = ""
)
