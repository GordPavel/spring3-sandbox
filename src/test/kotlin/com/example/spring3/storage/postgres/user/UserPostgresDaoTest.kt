package com.example.spring3.storage.postgres.user

import com.example.spring3.user.model.User
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(
    UserPostgresDaoTestConfiguration::class,
)
class UserPostgresDaoTest {
    @Autowired
    lateinit var userPostgresDao: UserPostgresDao

    @Autowired
    lateinit var userPostgresCrudRepository: UserPostgresCrudRepository

    @Test
    suspend fun name() {
        val createdUser = userPostgresDao.saveUser(
            User(
                null,
                "test",
                "test".toByteArray(),
                setOf("test")
            )
        )
        assertEquals(
            createdUser,
            userPostgresCrudRepository.findById(createdUser.id!!).awaitSingleOrNull()
        )
    }
}

@TestConfiguration
private class UserPostgresDaoTestConfiguration {
    @Bean
    fun userPostgresDao(
        userPostgresCrudRepository: UserPostgresCrudRepository
    ): UserPostgresDao = UserPostgresDao(
        userPostgresCrudRepository,
        Mappers.getMapper(UserPostgresDaoMapper::class.java)
    )
}