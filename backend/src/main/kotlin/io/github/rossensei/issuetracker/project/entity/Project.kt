package io.github.rossensei.issuetracker.project.entity

import io.github.rossensei.issuetracker.project.dto.response.ProjectResponse
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "projects")
class Project(
    @Id
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var description: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun toResponse(): ProjectResponse =
        ProjectResponse(
            id = this.id,
            name = this.name,
            description = this.description,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
}