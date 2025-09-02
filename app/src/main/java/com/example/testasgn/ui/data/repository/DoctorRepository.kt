package com.example.testasgn.ui.data.repository

import com.example.testasgn.ui.data.dao.DoctorDao
import com.example.testasgn.ui.data.model.Doctor
import kotlinx.coroutines.flow.Flow

class DoctorRepository(private val doctorDao: DoctorDao) {
    fun getDoctorById(doctorId: String): Flow<Doctor?> {
        return doctorDao.getDoctorById(doctorId)
    }
}