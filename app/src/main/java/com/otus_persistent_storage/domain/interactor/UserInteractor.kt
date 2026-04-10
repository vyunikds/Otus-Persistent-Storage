package com.otus_persistent_storage.domain.interactor

import com.otus_persistent_storage.domain.repository.IUserRepository
import com.otus_persistent_storage.presentation.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow


class UserInteractor(private val repository: IUserRepository) : IUserInteractor {

    private val refreshFlow = MutableSharedFlow<Unit>(replay = 1)
    
    init {
        refreshFlow.tryEmit(Unit)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadUsers(): Flow<UiState> = refreshFlow.asSharedFlow()
        .flatMapLatest { 
            loadUsersFlow()
        }

    fun loadUsersFlow(): Flow<UiState> = flow {
        emit(UiState(loading = true))

        val localUsers = repository.getUsers().firstOrNull() ?: emptyList()

        if (localUsers.isNotEmpty()) {
            emit(UiState(users = localUsers, loading = false))
        }

        try {
            repository.refreshUsers()
            val updatedUsers = repository.getUsers().firstOrNull() ?: emptyList()
            emit(UiState(users = updatedUsers, error = null, loading = false))
        } catch (e: Exception) {
            if (localUsers.isEmpty()) {
                emit(UiState(error = "Ошибка: ${e.message}", loading = false))
            } else {
                emit(UiState(users = localUsers, loading = false))
            }
        }
    }

    override fun refresh() {
        refreshFlow.tryEmit(Unit)
    }
}