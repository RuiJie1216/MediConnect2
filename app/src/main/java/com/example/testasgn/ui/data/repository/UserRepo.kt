package com.example.testasgn.ui.data.repository

import com.example.testasgn.ui.data.dao.UserDao
import com.example.testasgn.ui.data.dataTable.Users
import kotlinx.coroutines.flow.Flow

class UserRepo(private val userDao: UserDao) {

    fun getUserByIc(ic: String): Flow<Users?> = userDao.getUserByIc(ic)
    suspend fun insert(user: Users) = userDao.insert(user)

    suspend fun update(user: Users) = userDao.update(user)

    suspend fun delete(user: Users) = userDao.delete(user)
}