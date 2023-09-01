package com.example.spring3.user

import com.example.spring3.user.model.User
import reactor.core.publisher.Flux
import java.util.UUID

interface UserRepository {
    suspend fun findAll(): Flux<User>

    suspend fun findByUsername(username: String): User?

    suspend fun findById(id: UUID): User?

    suspend fun saveUser(user: User): User
}