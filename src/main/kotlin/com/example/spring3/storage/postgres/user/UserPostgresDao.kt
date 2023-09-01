package com.example.spring3.storage.postgres.user

import com.example.spring3.user.UserRepository
import com.example.spring3.user.model.User
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.UUID

@Component
class UserPostgresDao(
    private val userPostgresCrudRepository: UserPostgresCrudRepository,
    private val userPostgresDaoMapper: UserPostgresDaoMapper,
) : UserRepository {
    override suspend fun findAll(): Flux<User> = userPostgresCrudRepository.findAll().map(userPostgresDaoMapper::fromUserEntity)

    override suspend fun findByUsername(username: String): User? = userPostgresCrudRepository
        .findByUsername(username)
        .map(userPostgresDaoMapper::fromUserEntity)
        .awaitSingleOrNull()

    override suspend fun findById(id: UUID): User? = userPostgresCrudRepository
        .findById(id)
        .map(userPostgresDaoMapper::fromUserEntity)
        .awaitSingleOrNull()

    override suspend fun saveUser(user: User): User =
        userPostgresDaoMapper.toUserEntity(user)
            .let(userPostgresCrudRepository::save)
            .map(userPostgresDaoMapper::fromUserEntity)
            .awaitSingle()
}