package io.github.rossensei.issuetracker.projectmember.dto.request

import org.jetbrains.annotations.NotNull
import java.util.UUID

data class StoreProjectMemberRequest(
    @field:NotNull
    val userId: UUID,
)
