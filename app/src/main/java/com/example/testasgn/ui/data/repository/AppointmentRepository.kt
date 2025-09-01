package com.example.testasgn.ui.data.repository

import com.example.testasgn.ui.data.dao.AppointmentDao
import com.example.testasgn.ui.data.model.Appointment
import kotlinx.coroutines.flow.Flow

class AppointmentRepository(private val dao: AppointmentDao) {
    fun getAppointments(doctorId: String, date: String): Flow<List<Appointment>> {
        return dao.getAppointmentsForDoctor(doctorId, date)
    }

}