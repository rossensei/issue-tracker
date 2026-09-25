package io.github.rossensei.issuetracker.auth.service

import io.github.rossensei.issuetracker.auth.dto.RegisterRequest
import io.github.rossensei.issuetracker.auth.exception.DuplicateUserException
import io.github.rossensei.issuetracker.config.AuthenticatedUser
import io.github.rossensei.issuetracker.user.entity.User
import io.github.rossensei.issuetracker.user.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegistrationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun register(request: RegisterRequest): AuthenticatedUser {
        val email = request.email.trim().lowercase()
        val username = request.username.trim().lowercase()

        checkAvailable(email, username)

        val saved = try {
            userRepository.save(
                User(
                    username = username,
                    email = email,
                    password = passwordEncoder.encode(request.password)!!
                )
            )
        } catch (e: DataIntegrityViolationException) {
            checkAvailable(email, username)
            throw e
        }

        return AuthenticatedUser(saved.id, saved.email, saved.username)
    }

    private fun checkAvailable(email: String, username: String) {
        if (userRepository.existsByEmail(email)) throw DuplicateUserException("email")
        if (userRepository.existsByUsername(username)) throw DuplicateUserException("username")
    }
}
