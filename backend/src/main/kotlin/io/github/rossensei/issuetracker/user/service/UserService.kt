package io.github.rossensei.issuetracker.user.service

import io.github.rossensei.issuetracker.user.dto.request.StoreUserRequest
import io.github.rossensei.issuetracker.user.repository.UserRepository
import io.github.rossensei.issuetracker.user.dto.response.UserResponse
import io.github.rossensei.issuetracker.user.entity.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getAllUsers(): List<UserResponse> = userRepository.findAll().map { it.toResponse() }

    @Transactional
    fun storeUser(request: StoreUserRequest): UserResponse =
        userRepository.save(
            User(
                username = request.username,
                email = request.email,
                password = request.password
            )
        ).toResponse()
}