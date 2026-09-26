package io.github.rossensei.issuetracker.project.controller

import io.github.rossensei.issuetracker.project.dto.request.StoreProjectRequest
import io.github.rossensei.issuetracker.project.dto.request.UpdateProjectRequest
import io.github.rossensei.issuetracker.project.dto.response.PageResponse
import io.github.rossensei.issuetracker.project.dto.response.ProjectResponse
import io.github.rossensei.issuetracker.project.service.ProjectService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
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
@RequestMapping("/api/projects")
@Tag(name = "project", description = "Projects API")
class ProjectController(
    private val projectService: ProjectService,
) {
    @GetMapping
    fun getAllProjects(@ParameterObject pageable: Pageable): PageResponse<ProjectResponse> {
        val userId = UUID.fromString("d08e567b-aeae-4a9d-b2f5-6c7edb4d2cae")
        return projectService.getMyProjects(userId, pageable)
    }

    @PostMapping
    fun storeProject(@Valid @RequestBody request: StoreProjectRequest): ProjectResponse {
        val userId = UUID.fromString("d08e567b-aeae-4a9d-b2f5-6c7edb4d2cae")
        return projectService.storeProject(userId, request)
    }

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID): ProjectResponse =
        projectService.getProject(projectId)

    @PatchMapping("/{projectId}")
    fun updateProject(
        @PathVariable projectId: UUID,
        @Valid @RequestBody request: UpdateProjectRequest
    ): ProjectResponse =
        projectService.updateProject(projectId, request)

    @DeleteMapping("/{projectId}")
    fun deleteProject(@PathVariable projectId: UUID) =
        projectService.deleteProject(projectId)
}