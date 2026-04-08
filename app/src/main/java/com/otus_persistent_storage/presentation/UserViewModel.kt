package com.otus_persistent_storage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otus_persistent_storage.data.room.UserEntity
import com.otus_persistent_storage.domain.interactor.IUserInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class UiState(
    val users: List<UserEntity> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)

class UserViewModel(private val userInteractor: IUserInteractor) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(loading = true))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        userInteractor.loadUsers()
            .onEach { state ->
                _uiState.value = state
            }
            .launchIn(viewModelScope)
    }

    fun loadUsers() {
        userInteractor.refresh()
    }
}