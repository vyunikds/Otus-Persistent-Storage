package com.otus_persistent_storage.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)