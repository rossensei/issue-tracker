package io.github.rossensei.issuetracker.user.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class UserResponse(
    val id: UUID = UUID.randomUUID(),
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now()
)
