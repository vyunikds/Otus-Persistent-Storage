package com.otus_persistent_storage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.otus_persistent_storage.di.AppModule
import com.otus_persistent_storage.di.UserViewModelFactory
import com.otus_persistent_storage.presentation.ui.UserScreen
import com.otus_persistent_storage.presentation.UserViewModel

class MainActivity : ComponentActivity() {
    private lateinit var appModule: AppModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appModule = AppModule(this)

        setContent {
            MaterialTheme {
                val viewModel: UserViewModel = viewModel(
                    factory = UserViewModelFactory(appModule.userInteractor)
                )
                val uiState by viewModel.uiState.collectAsState()

                UserScreen(
                    users = uiState.users,
                    loading = uiState.loading,
                    error = uiState.error,
                    onRetry = { viewModel.loadUsers() }
                )
            }
        }
    }
}