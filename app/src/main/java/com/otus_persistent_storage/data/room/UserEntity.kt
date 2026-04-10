package com.otus_persistent_storage.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.otus_persistent_storage.data.dto.UserDto

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val phone: String
)

fun UserDto.toEntity() = UserEntity(
    id,
    name,
    email,
    phone
)