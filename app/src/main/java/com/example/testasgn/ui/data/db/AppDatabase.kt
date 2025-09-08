package com.example.testasgn.ui.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.testasgn.ui.data.dao.DoctorDao
import com.example.testasgn.ui.data.model.Doctor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Doctor::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun doctorDao(): DoctorDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                getDatabase(context).doctorDao().insertAll(prepopulateDoctors())
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private fun prepopulateDoctors(): List<Doctor> = listOf(
            Doctor(
                doctorId = "D001",
                loginId = "0001",
                docName = "Dr. Jason Lim",
                docDegree = "MBBS, MPH",
                docSpecialty = "Family Medicine",
                yearOfPractice = 6,
                language = "English, Malay",
                dayOff = "Friday",
                quote = "Your family’s health, our priority."
            ),
            Doctor(
                doctorId = "D002",
                loginId = "0002",
                docName = "Dr. Alicia Tan",
                docDegree = "MBBS, MD",
                docSpecialty = "Pediatrics",
                yearOfPractice = 8,
                language = "English, Mandarin",
                dayOff = "Sunday",
                quote = "Caring for little ones with big hearts."
            ),
            Doctor(
                doctorId = "D003",
                loginId = "0003",
                docName = "Dr. Raj Kumar",
                docDegree = "MBBS, MS",
                docSpecialty = "Orthopedics",
                yearOfPractice = 12,
                language = "English, Tamil",
                dayOff = "Monday",
                quote = "Stronger bones, stronger life."
            ),
            Doctor(
                doctorId = "D004",
                loginId = "0004",
                docName = "Dr. Sarah Wong",
                docDegree = "MBBS, MRCP",
                docSpecialty = "Cardiology",
                yearOfPractice = 10,
                language = "English, Cantonese",
                dayOff = "Wednesday",
                quote = "Keeping every heartbeat safe."
            ),
            Doctor(
                doctorId = "D005",
                loginId = "0005",
                docName = "Dr. Amir Hassan",
                docDegree = "MBBS, FRCS",
                docSpecialty = "General Surgery",
                yearOfPractice = 15,
                language = "English, Malay, Arabic",
                dayOff = "Saturday",
                quote = "Precision in every cut, care in every step."
            ),
            Doctor(
                doctorId = "D006",
                loginId = "0006",
                docName = "Dr. Emily Chan",
                docDegree = "MBBS, MD",
                docSpecialty = "Dermatology",
                yearOfPractice = 5,
                language = "English, Mandarin",
                dayOff = "Tuesday",
                quote = "Healthy skin, confident you."
            ),
            Doctor(
                doctorId = "D007",
                loginId = "0007",
                docName = "Dr. Michael Lee",
                docDegree = "MBBS, MD",
                docSpecialty = "Neurology",
                yearOfPractice = 11,
                language = "English, Korean",
                dayOff = "Thursday",
                quote = "Mind and nerves in harmony."
            ),
            Doctor(
                doctorId = "D008",
                loginId = "0008",
                docName = "Dr. Priya Nair",
                docDegree = "MBBS, MD",
                docSpecialty = "Obstetrics & Gynecology",
                yearOfPractice = 9,
                language = "English, Hindi",
                dayOff = "Monday",
                quote = "Compassionate care for every woman."
            ),
            Doctor(
                doctorId = "D009",
                loginId = "0009",
                docName = "Dr. Kelvin Ong",
                docDegree = "MBBS, MPH",
                docSpecialty = "Public Health",
                yearOfPractice = 7,
                language = "English, Malay",
                dayOff = "Friday",
                quote = "Building healthier communities together."
            ),
            Doctor(
                doctorId = "D010",
                loginId = "0010",
                docName = "Dr. Hana Abdullah",
                docDegree = "MBBS, MD",
                docSpecialty = "Psychiatry",
                yearOfPractice = 13,
                language = "English, Malay",
                dayOff = "Sunday",
                quote = "Healing minds, restoring hope."
            )
        )

    }
}