package com.example.spring3.user.service

import com.example.spring3.user.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.util.UUID

class UserServiceTest {
    private val userRepository = Mockito.mock(UserRepository::class.java)

    private val service = UserService(userRepository)

    @Test
    suspend fun name() {
        val uuid = UUID.randomUUID()
        Mockito.`when`(userRepository.findById(ArgumentMatchers.eq(uuid))).thenReturn(null)
        assertEquals(null, service.findById(uuid))
    }
}