package com.example.testasgn.ui.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.testasgn.ui.data.model.Doctor
import kotlinx.coroutines.flow.Flow



@Dao
interface DoctorDao {
    @Query("SELECT * FROM Doctor WHERE loginId = :loginId LIMIT 1")
    suspend fun getDoctorByLoginId(loginId: String): Doctor?

}