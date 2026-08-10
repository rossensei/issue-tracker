package io.github.rossensei.issuetracker.project.dto.request

data class ProjectRequest(
    val id: String,
    val name: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
)
