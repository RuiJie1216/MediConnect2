package com.example.testasgn.ui.data.repository

import com.example.testasgn.ui.data.dao.DoctorDao
import com.example.testasgn.ui.data.model.Doctor

class DoctorRepository(private val doctorDao: DoctorDao) {

    suspend fun getDoctorByLoginId(loginId: String): Doctor? {
        return doctorDao.getDoctorByLoginId(loginId)
    }
}