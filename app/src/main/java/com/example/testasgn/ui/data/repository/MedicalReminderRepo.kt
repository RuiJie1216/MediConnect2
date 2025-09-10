package com.example.testasgn.ui.data.repository

import com.example.testasgn.ui.data.dataTable.MedicalReminder
import com.example.testasgn.ui.data.dao.MedicalDao

class MedicalReminderRepo(private val medicalDao: MedicalDao) {
    suspend fun insertReminder(reminder: MedicalReminder) {
        medicalDao.insertReminder(reminder)
    }

    suspend fun insertReminders(reminders: List<MedicalReminder>) {
        medicalDao.insertReminders(reminders)
    }

    suspend fun updateReminder(reminder: MedicalReminder) {
        medicalDao.updateReminder(reminder)
    }

    suspend fun deleteReminder(reminder: MedicalReminder) {
        medicalDao.deleteReminder(reminder)
    }

    suspend fun getRemindersByUser(userIc: String): List<MedicalReminder> {
        return medicalDao.getRemindersByUser(userIc)
    }

    suspend fun getRemindersByDate(userIc: String, date: String): List<MedicalReminder> {
        return medicalDao.getRemindersByDate(userIc, date)
    }
}