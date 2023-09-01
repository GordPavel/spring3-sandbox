package com.example.spring3.user.controller

import com.example.spring3.user.controller.dto.NewUserDto
import com.example.spring3.user.controller.dto.UserDto
import com.example.spring3.user.model.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserControllerMapper {
    fun fromNewUserDto(user: NewUserDto): User
    fun toUserDto(user: User): UserDto
}