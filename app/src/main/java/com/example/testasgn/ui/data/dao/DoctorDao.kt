package com.example.testasgn.ui.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testasgn.ui.data.DataTable.Doctors
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doctor: Doctors)

    @Update
    suspend fun update(doctor: Doctors)

    @Delete
    suspend fun delete(doctor: Doctors)

    @Query("SELECT * FROM doctors WHERE id = :id")
    fun getDoctorById(id: String): Flow<Doctors?>

}