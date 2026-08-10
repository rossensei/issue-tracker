package io.github.rossensei.issuetracker.issue.dto.request

import io.github.rossensei.issuetracker.issue.enums.IssuePriority
import io.github.rossensei.issuetracker.issue.enums.IssueStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

data class StoreIssueRequest(
    @field:NotBlank(message = "Title cannot be blank.")
    @field:Size(max = 255, message = "Title must between 1 and 255")
    val title: String,
    @field:Size(max = 10_000, message = "Maximum character length is 10,000.")
    val description: String? = null,
    val priority: IssuePriority = IssuePriority.MEDIUM,
    val assignedTo: UUID? = null,
)
