package com.example.testasgn.ui.data.DataTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users",)
data class Users(
    @PrimaryKey
    @ColumnInfo(name = "ic")
    val ic: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "age")
    val age: Int = 0,

    @ColumnInfo(name = "weight")
    val weight: Double = 0.0,

    @ColumnInfo(name = "height")
    val height: Double = 0.0,

    @ColumnInfo(name = "address")
    val address: String = ""
)
