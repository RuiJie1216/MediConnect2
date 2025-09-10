package com.example.testasgn.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.testasgn.ui.data.DataTable.Doctors
import com.example.testasgn.ui.data.DataTable.MedicalReminder
import com.example.testasgn.ui.data.dao.UserDao
import com.example.testasgn.ui.data.DataTable.Users
import com.example.testasgn.ui.data.dao.DoctorDao
import com.example.testasgn.ui.data.dao.MedicalDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Users::class, Doctors::class, MedicalReminder::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun doctorDao(): DoctorDao
    abstract fun medicalDao(): MedicalDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { database ->  // 使用 INSTANCE
                                CoroutineScope(Dispatchers.IO).launch {
                                    database.medicalDao().insertReminders(
                                        listOf(
                                            MedicalReminder(
                                                ic = "061010031937",
                                                name = "Paracetamol",
                                                dose = 2,
                                                instruction = "After food",
                                                time = "09:00 AM",
                                                date = "2025-09-10"
                                            ),
                                            MedicalReminder(
                                                ic = "061010031937",
                                                name = "Vitamin C",
                                                dose = 1,
                                                instruction = "Before food",
                                                time = "08:00 PM",
                                                date = "2025-09-10"
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}