package com.example.spring3.user.controller

import com.example.spring3.user.controller.dto.NewUserDto
import com.example.spring3.user.controller.dto.UserDto
import com.example.spring3.user.model.SearchAllCriteria
import com.example.spring3.user.model.UsernameSearchCriteria
import com.example.spring3.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val userControllerMapper: UserControllerMapper,
) {
    @GetMapping
    suspend fun findUsers(
        @RequestParam username: String?
    ): Flux<UserDto> {
        val searchCriteria = username?.let { UsernameSearchCriteria(it) } ?: SearchAllCriteria
        return userService
            .findUsers(searchCriteria)
            .map(userControllerMapper::toUserDto)
    }

    @GetMapping("/{id}")
    suspend fun findById(
        @PathVariable id: UUID,
    ): ResponseEntity<UserDto> = userService
        .findById(id)
        ?.let(userControllerMapper::toUserDto)
        ?.let { ResponseEntity.ok(it) }
        ?: ResponseEntity.notFound().build()

    @PostMapping
    suspend fun saveUser(
        @RequestBody user: NewUserDto,
    ): UserDto = userService
        .saveUser(userControllerMapper.fromNewUserDto(user))
        .let(userControllerMapper::toUserDto)
}