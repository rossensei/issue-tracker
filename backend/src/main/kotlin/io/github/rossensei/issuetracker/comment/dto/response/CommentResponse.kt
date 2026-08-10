package io.github.rossensei.issuetracker.comment.dto.response

import io.github.rossensei.issuetracker.issue.dto.response.IssueResponse
import io.github.rossensei.issuetracker.user.dto.response.UserResponse
import java.time.LocalDateTime
import java.util.UUID

data class CommentResponse(
    val id: UUID,
    val issue: IssueResponse,
    val user: UserResponse,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
