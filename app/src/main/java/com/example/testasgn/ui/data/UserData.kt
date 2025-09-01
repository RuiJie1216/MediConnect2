package com.example.testasgn.ui.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserData(
    val ic: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = ""
)


@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val ic: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = ""
)