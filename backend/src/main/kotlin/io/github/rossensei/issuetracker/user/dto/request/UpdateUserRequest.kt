package io.github.rossensei.issuetracker.user.dto.request

data class UpdateUserRequest(
    val username: String,
    val email: String,
    val password: String,
)
