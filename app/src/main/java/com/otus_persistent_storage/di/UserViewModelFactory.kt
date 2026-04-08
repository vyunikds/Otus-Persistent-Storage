package com.otus_persistent_storage.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.otus_persistent_storage.domain.interactor.IUserInteractor
import com.otus_persistent_storage.presentation.UserViewModel

class UserViewModelFactory(private val userInteractor: IUserInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userInteractor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}