package io.github.rossensei.issuetracker.user.entity

import io.github.rossensei.issuetracker.user.dto.response.UserResponse
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "users")
class User (
    @Id
    val id: UUID = UUID.randomUUID(),
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun toResponse(): UserResponse =
        UserResponse(
            id = this.id,
            username = this.username,
            email = this.email,
            password = this.password,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
}