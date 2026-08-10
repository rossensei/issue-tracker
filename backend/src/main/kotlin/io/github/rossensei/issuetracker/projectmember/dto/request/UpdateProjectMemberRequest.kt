package io.github.rossensei.issuetracker.projectmember.dto.request

import io.github.rossensei.issuetracker.projectmember.enums.ProjectMemberRole
import org.jetbrains.annotations.NotNull

data class UpdateProjectMemberRequest(
    @field:NotNull
    val role: ProjectMemberRole,
)
