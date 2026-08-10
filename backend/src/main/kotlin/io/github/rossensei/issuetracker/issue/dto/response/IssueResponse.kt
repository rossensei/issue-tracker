package io.github.rossensei.issuetracker.issue.dto.response

import io.github.rossensei.issuetracker.issue.enums.IssuePriority
import io.github.rossensei.issuetracker.issue.enums.IssueStatus
import io.github.rossensei.issuetracker.project.dto.response.ProjectResponse
import io.github.rossensei.issuetracker.user.dto.response.UserResponse
import java.time.LocalDateTime
import java.util.UUID

data class IssueResponse(
    val id: UUID,
    val title: String,
    val description: String? = null,
    val project: ProjectResponse,
    val status: IssueStatus,
    val priority: IssuePriority,
    val createdBy: UserResponse,
    val assignedTo: UserResponse? = null,
    val updatedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
)
