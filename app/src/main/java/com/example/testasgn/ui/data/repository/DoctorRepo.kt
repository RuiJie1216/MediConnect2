package com.example.testasgn.ui.data.repository

import com.example.testasgn.ui.data.DataTable.Doctors
import com.example.testasgn.ui.data.dao.DoctorDao
import kotlinx.coroutines.flow.Flow

class DoctorRepo(private val doctorDao: DoctorDao) {
    fun getDoctorById(id: String): Flow<Doctors?> = doctorDao.getDoctorById(id)

    suspend fun insert(doctor: Doctors) = doctorDao.insert(doctor)

    suspend fun update(doctor: Doctors) = doctorDao.update(doctor)

    suspend fun delete(doctor: Doctors) = doctorDao.delete(doctor)

}