package io.github.rossensei.issuetracker.comment.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateCommentRequest(
    @field:NotBlank
    @field:Size(max = 10_000)
    val content: String
)
