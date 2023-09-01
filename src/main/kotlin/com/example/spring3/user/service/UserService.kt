package com.example.spring3.user.service

import com.example.spring3.user.UserRepository
import com.example.spring3.user.model.SearchAllCriteria
import com.example.spring3.user.model.User
import com.example.spring3.user.model.UserSearchCriteria
import com.example.spring3.user.model.UsernameSearchCriteria
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    suspend fun saveUser(user: User): User = userRepository.saveUser(user)

    suspend fun findUsers(searchCriteria: UserSearchCriteria): Flux<User> = when (searchCriteria) {
        is SearchAllCriteria      -> userRepository.findAll()
        is UsernameSearchCriteria -> userRepository
            .findByUsername(searchCriteria.username)
            ?.let { Flux.just(it) }
            ?: Flux.empty()
    }

    suspend fun findById(id: UUID): User? = userRepository.findById(id)
}