package com.example.spring3.user.controller.dto

import java.util.UUID

data class UserDto(
    val id: UUID,
    val username: String,
    val password: ByteArray,
    val roles: Set<String>,
)
