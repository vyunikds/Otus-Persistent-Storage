package com.otus_persistent_storage.domain.repository

import com.otus_persistent_storage.data.room.UserEntity
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getUsers(): Flow<List<UserEntity>>
    suspend fun refreshUsers()
}