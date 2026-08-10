package io.github.rossensei.issuetracker.user.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class StoreUserRequest(
    @field:NotEmpty(message = "Title must not be empty.")
    @field:Size(max = 100, message = "Title must between 1 and 100.")
    val username: String,
    @field:NotBlank(message = "Title must not be blank.")
    @field:Email(message = "Invalid email address.")
    @field:Size(max = 100, message = "Title must between 1 and 100.")
    val email: String,
    @field:NotBlank(message = "Password cannot be blank.")
    @field:Size(
        min = 8,
        max = 72,
        message = "Password must be between 8 and 72 characters."
    )
    val password: String,
)
