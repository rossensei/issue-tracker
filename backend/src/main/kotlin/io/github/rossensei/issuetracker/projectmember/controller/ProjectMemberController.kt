package io.github.rossensei.issuetracker.projectmember.controller

import io.github.rossensei.issuetracker.projectmember.dto.request.StoreProjectMemberRequest
import io.github.rossensei.issuetracker.projectmember.dto.request.UpdateProjectMemberRequest
import io.github.rossensei.issuetracker.projectmember.dto.response.ProjectMemberResponse
import io.github.rossensei.issuetracker.projectmember.service.ProjectMemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/projects/{projectId}/members")
class ProjectMemberController(
    private val projectMemberService: ProjectMemberService
) {
    @GetMapping
    fun index(@PathVariable projectId: UUID): List<ProjectMemberResponse> =
        projectMemberService.getProjectMembers(projectId)

    @PostMapping
    fun store(
        @PathVariable projectId: UUID,
        @Valid @RequestBody request: StoreProjectMemberRequest
    ): ProjectMemberResponse =
        projectMemberService.addProjectMember(projectId, request)

    @PatchMapping("/{userId}")
    fun update(
        @PathVariable projectId: UUID,
        @PathVariable userId: UUID,
        @Valid @RequestBody request: UpdateProjectMemberRequest
    ): ProjectMemberResponse =
        projectMemberService.updateProjectMember(projectId, userId, request)

    @DeleteMapping("/{userId}")
    fun delete(@PathVariable projectId: UUID, @PathVariable userId: UUID) =
        projectMemberService.removeProjectMember(projectId, userId)
}