package com.example.spring3.storage.postgres.user

import com.example.spring3.user.model.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserPostgresDaoMapper {
    fun toUserEntity(user: User): UserPostgresEntity
    fun fromUserEntity(user: UserPostgresEntity): User
}