package com.otus_persistent_storage.domain.repository

import com.otus_persistent_storage.data.room.UserEntity
import com.otus_persistent_storage.data.room.toEntity
import com.otus_persistent_storage.data.source.UserLocalDataSourceImpl
import com.otus_persistent_storage.data.source.UserRemoteDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSourceImpl,
    private val remoteDataSource: UserRemoteDataSourceImpl
) : IUserRepository {

    override fun getUsers(): Flow<List<UserEntity>> {
        return localDataSource.getAllUsers()
    }

    override suspend fun refreshUsers() {
        try {
            val usersFlow = remoteDataSource.getUsers()
            usersFlow.collect { users ->
                if (users.isNotEmpty()) {
                    localDataSource.insertAll(users.map { it.toEntity() })
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}