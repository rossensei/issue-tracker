package io.github.rossensei.issuetracker.auth.dto

data class LoginRequest(
    val username: String,
    val password: String,
)
