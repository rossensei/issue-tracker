package io.github.rossensei.issuetracker.issue.dto.request

import io.github.rossensei.issuetracker.issue.enums.IssuePriority
import io.github.rossensei.issuetracker.issue.enums.IssueStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

data class UpdateIssueRequest(
    @field:NotBlank(message = "Title must not be empty.")
    @field:Size(max = 255, message = "Title must between 1 and 255")
    val title: String,
    @field:Size(max = 10_000, message = "Description max length is 10,000 characters.")
    val description: String,
    val status: IssueStatus,
    val priority: IssuePriority,
    val assignedTo: UUID? = null,
)
