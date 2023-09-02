package com.example.spring3.user.controller

import com.example.spring3.user.service.UserService
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import

@WebMvcTest(
    UserController::class,
)
@Import(UserControllerTestConfiguration::class)
class UserControllerTest {
    @MockBean
    lateinit var userService: UserService
}

@TestConfiguration
private class UserControllerTestConfiguration {

}