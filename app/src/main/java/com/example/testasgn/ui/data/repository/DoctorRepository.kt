package com.example.testasgn.ui.data.repository

import com.example.testasgn.ui.data.dao.DoctorDao
import com.example.testasgn.ui.data.model.Doctor

class DoctorRepository(private val doctorDao: DoctorDao) {

    // 获取所有医生
    suspend fun getAllDoctors(): List<Doctor> {
        return doctorDao.getAllDoctors()
    }

    // 根据 ID 获取医生
    suspend fun getDoctorById(id: String): Doctor? {
        return doctorDao.getDoctorById(id)
    }

    // （可选）新增医生
    suspend fun insertDoctor(doctor: Doctor) {
        doctorDao.insertAll(listOf(doctor))
    }

    // （可选）一次性插入多个
    suspend fun insertDoctors(doctors: List<Doctor>) {
        doctorDao.insertAll(doctors)
    }
}