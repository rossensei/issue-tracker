package io.github.rossensei.issuetracker.project.controller

import io.github.rossensei.issuetracker.project.dto.request.StoreProjectRequest
import io.github.rossensei.issuetracker.project.dto.request.UpdateProjectRequest
import io.github.rossensei.issuetracker.project.dto.response.ProjectResponse
import io.github.rossensei.issuetracker.project.service.ProjectService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
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
class ProjectController(
    private val projectService: ProjectService,
) {
    @GetMapping
    fun index(pageable: Pageable): Page<ProjectResponse> =
        projectService.getAllProjects(pageable)

    @PostMapping
    fun store(@Valid @RequestBody request: StoreProjectRequest): ProjectResponse =
        projectService.storeProject(request)

    @GetMapping("/{projectId}")
    fun store(@PathVariable projectId: UUID): ProjectResponse =
        projectService.getProject(projectId)

    @PatchMapping("/{projectId}")
    fun update(
        @PathVariable projectId: UUID,
        @Valid @RequestBody request: UpdateProjectRequest
    ): ProjectResponse =
        projectService.updateProject(projectId, request)

    @DeleteMapping("/{projectId}")
    fun destroy(@PathVariable projectId: UUID) =
        projectService.deleteProject(projectId)
}