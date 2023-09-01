package com.example.spring3.user.controller.dto

data class NewUserDto(
    val username: String,
    val password: ByteArray,
    val roles: Set<String>,
)
