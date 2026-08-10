package io.github.rossensei.issuetracker.projectmember.dto.response

import io.github.rossensei.issuetracker.project.entity.Project
import io.github.rossensei.issuetracker.projectmember.enums.ProjectMemberRole
import io.github.rossensei.issuetracker.user.entity.User
import java.time.LocalDateTime
import java.util.UUID

data class ProjectMemberResponse(
    val id: UUID,
    val project: Project,
    val user: User,
    val role: ProjectMemberRole,
    val joinedAt: LocalDateTime,
)
