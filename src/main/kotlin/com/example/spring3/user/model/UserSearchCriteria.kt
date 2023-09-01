package com.example.spring3.user.model

sealed interface UserSearchCriteria

object SearchAllCriteria: UserSearchCriteria

data class UsernameSearchCriteria(
    val username: String,
) : UserSearchCriteria
