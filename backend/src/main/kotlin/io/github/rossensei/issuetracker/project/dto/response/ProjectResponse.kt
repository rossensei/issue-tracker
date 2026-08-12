package io.github.rossensei.issuetracker.project.dto.response

import io.github.rossensei.issuetracker.user.dto.response.UserResponse
import java.time.LocalDateTime
import java.util.UUID

data class ProjectResponse(
    val id: UUID = UUID.randomUUID(),
    val name: String? = null,
    val description: String? = null,
    val owner: UserResponse? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)
