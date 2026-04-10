package com.otus_persistent_storage.di

import android.content.Context
import com.otus_persistent_storage.data.ktor.KtorClient
import com.otus_persistent_storage.data.ktor.UserApiService
import com.otus_persistent_storage.data.room.AppDatabase
import com.otus_persistent_storage.data.source.UserLocalDataSourceImpl
import com.otus_persistent_storage.data.source.UserRemoteDataSourceImpl
import com.otus_persistent_storage.domain.interactor.IUserInteractor
import com.otus_persistent_storage.domain.interactor.UserInteractor
import com.otus_persistent_storage.domain.repository.IUserRepository
import com.otus_persistent_storage.domain.repository.UserRepositoryImpl

class AppModule(private val context: Context) {

    val database by lazy { AppDatabase.getDatabase(context) }

    val userLocalDataSource: UserLocalDataSourceImpl by lazy {
        UserLocalDataSourceImpl(database.userDao())
    }

    val userRemoteDataSource: UserRemoteDataSourceImpl by lazy {
        UserRemoteDataSourceImpl(UserApiService(KtorClient.httpClient))
    }

    val userRepository: IUserRepository by lazy {
        UserRepositoryImpl(userLocalDataSource, userRemoteDataSource)
    }

    val userInteractor: IUserInteractor by lazy {
        UserInteractor(userRepository)
    }
}