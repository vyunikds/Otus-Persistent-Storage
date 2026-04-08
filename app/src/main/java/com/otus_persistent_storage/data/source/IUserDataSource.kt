package com.otus_persistent_storage.data.source

import com.otus_persistent_storage.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface IUserDataSource {
    suspend fun getUsers(): Flow<List<UserDto>>
}