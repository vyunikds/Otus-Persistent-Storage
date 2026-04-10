package com.otus_persistent_storage.data.source

import com.otus_persistent_storage.data.ktor.UserApiService
import com.otus_persistent_storage.data.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRemoteDataSourceImpl(private val userApiService: UserApiService) : IUserDataSource {

    override suspend fun getUsers(): Flow<List<UserDto>> {
        return flow {
            val users = userApiService.getUsers()
            emit(users)
        }
    }
}