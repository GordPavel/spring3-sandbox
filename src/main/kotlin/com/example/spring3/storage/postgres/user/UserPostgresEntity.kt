package com.example.spring3.storage.postgres.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("users")
data class UserPostgresEntity(
    @Id
    val id: UUID?,
    val username: String,
    val password: ByteArray,
    val roles: Set<String>,
)