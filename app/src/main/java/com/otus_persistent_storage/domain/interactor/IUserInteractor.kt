package com.otus_persistent_storage.domain.interactor

import com.otus_persistent_storage.presentation.UiState
import kotlinx.coroutines.flow.Flow

interface IUserInteractor {
    fun loadUsers(): Flow<UiState>
    fun refresh()
}