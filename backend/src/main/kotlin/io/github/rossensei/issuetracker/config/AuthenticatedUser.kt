package io.github.rossensei.issuetracker.config

import java.util.UUID

data class AuthenticatedUser(
    val id: UUID,
    val email: String,
    val username: String,
)
