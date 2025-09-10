package com.example.testasgn.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.testasgn.ui.data.dataTable.Doctors
import com.example.testasgn.ui.data.dataTable.MedicalReminder
import com.example.testasgn.ui.data.dao.UserDao
import com.example.testasgn.ui.data.dataTable.Users
import com.example.testasgn.ui.data.dao.DoctorDao
import com.example.testasgn.ui.data.dao.MedicalDao
import com.google.firebase.firestore.auth.User
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
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}