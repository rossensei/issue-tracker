package io.github.rossensei.issuetracker.project.service

import io.github.rossensei.issuetracker.project.dto.request.StoreProjectRequest
import io.github.rossensei.issuetracker.project.dto.request.UpdateProjectRequest
import io.github.rossensei.issuetracker.project.dto.response.PageResponse
import io.github.rossensei.issuetracker.project.dto.response.ProjectResponse
import io.github.rossensei.issuetracker.project.entity.Project
import io.github.rossensei.issuetracker.project.repository.ProjectRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.UUID

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
) {
    fun getAllProjects(pageable: Pageable): PageResponse<ProjectResponse> =
        projectRepository
            .findAll(pageable)
            .map { it.toResponse() }
            .let { page ->
                PageResponse(
                    content = page.content,
                    page = page.number,
                    size = page.size,
                    totalElements = page.totalElements,
                    totalPages = page.totalPages
                )
            }

    fun storeProject(request: StoreProjectRequest): ProjectResponse =
        projectRepository.save(
            Project(
                name = request.name,
                description = request.description
            )
        ).toResponse()

    fun getProject(projectId: UUID): ProjectResponse =
        projectRepository
            .findById(projectId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Project with ID: $projectId does not exist"
                )
            }.toResponse()

    @Transactional
    fun updateProject(projectId: UUID, request: UpdateProjectRequest): ProjectResponse =
        projectRepository
            .findById(projectId)
            .map { p ->
                p.name = request.name
                p.description = request.description
                p.updatedAt = LocalDateTime.now()

                projectRepository.save(p).toResponse()
            }
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Project with ID: $projectId does not exist"
                )
            }
    @Transactional
    fun deleteProject(projectId: UUID) =
        projectRepository.deleteById(projectId)
}