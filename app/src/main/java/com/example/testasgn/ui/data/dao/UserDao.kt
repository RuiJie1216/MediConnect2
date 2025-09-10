package com.example.testasgn.ui.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testasgn.ui.data.DataTable.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: Users)

    @Update
    suspend fun update(user: Users)

    @Delete
    suspend fun delete(user: Users)

    @Query("SELECT * FROM users WHERE ic = :ic")
    fun getUserByIc(ic: String): Flow<Users?>

}