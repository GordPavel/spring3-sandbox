package com.example.spring3.storage.postgres.user

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface UserPostgresCrudRepository : ReactiveCrudRepository<UserPostgresEntity, UUID> {
    fun findByUsername(username: String): Mono<UserPostgresEntity>
}