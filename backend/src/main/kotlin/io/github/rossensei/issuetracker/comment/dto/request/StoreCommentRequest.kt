package io.github.rossensei.issuetracker.comment.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class StoreCommentRequest(
    @field:NotNull
    val userId: UUID,
    @field:NotBlank(message = "Comment cannot be blank")
    @field:Size(
        max = 10_000,
        message = "Comment cannot be blank"
    )
    val content: String
)
