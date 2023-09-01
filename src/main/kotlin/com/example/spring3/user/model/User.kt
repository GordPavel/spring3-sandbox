package com.example.spring3.user.model

import java.util.UUID

data class User(
    val id: UUID?,
    val username: String,
    val password: ByteArray,
    val roles: Set<String>,
)
