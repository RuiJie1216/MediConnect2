package com.example.testasgn.ui.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE ic = :ic")
    suspend fun getUserByIC(ic: String): UserEntity?

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<UserEntity>
}