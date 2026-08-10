package io.github.rossensei.issuetracker.project.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateProjectRequest(
    @field:NotBlank(message = "Title cannot be blank.")
    @field:Size(max = 255, message = "Title must between 1 and 255")
    val name: String,
    val description: String? = null,
)
