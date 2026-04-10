package com.otus_persistent_storage.data.source

import com.otus_persistent_storage.data.dto.UserDto
import com.otus_persistent_storage.data.room.UserEntity
import com.otus_persistent_storage.data.room.dao.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLocalDataSourceImpl(private val userDao: UserDao) : IUserDataSource {

    override suspend fun getUsers(): Flow<List<UserDto>> {
        return userDao.getAllUsers().map { entities -> entities.map { it.toUser() } }
    }

    fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    suspend fun insertAll(users: List<UserEntity>) {
        userDao.insertAll(users)
    }
}

fun UserEntity.toUser() = UserDto(
    id,
    name,
    email,
    phone
)