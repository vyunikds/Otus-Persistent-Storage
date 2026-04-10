package com.otus_persistent_storage.data.ktor

import android.util.Log
import com.otus_persistent_storage.data.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserApiService(private val client: HttpClient) {
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    suspend fun getUsers(): List<UserDto> {
        return try {
            val url = "$baseUrl/users"
            val response = client.get(url)
            val body: List<UserDto> = response.body()
            body
        } catch (e: Exception) {
            Log.e("UserApiService", "Error fetching users: ${e.message}", e)
            throw UserApiFetchException(e)
        }
    }
}