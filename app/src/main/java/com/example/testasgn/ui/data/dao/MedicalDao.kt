package com.example.testasgn.ui.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testasgn.ui.data.dataTable.MedicalReminder

@Dao
interface MedicalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: MedicalReminder)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminders(reminders: List<MedicalReminder>)

    @Update
    suspend fun updateReminder(reminder: MedicalReminder)

    @Delete
    suspend fun deleteReminder(reminder: MedicalReminder)

    // find medicine by user IC
    @Query("SELECT * FROM medical_reminder WHERE user_ic = :userIc ORDER BY date, time")
    suspend fun getRemindersByUser(userIc: String): List<MedicalReminder>

    // find medicine by user IC and date
    @Query("SELECT * FROM medical_reminder WHERE user_ic = :userIc AND date = :date ORDER BY time")
    suspend fun getRemindersByDate(userIc: String, date: String): List<MedicalReminder>

}