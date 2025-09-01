package com.example.testasgn.ui.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.testasgn.ui.data.model.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Query("SELECT * FROM Appointment WHERE doctorId = :doctorId AND date = :date")
    fun getAppointmentsForDoctor(doctorId: String, date: String): Flow<List<Appointment>>


}