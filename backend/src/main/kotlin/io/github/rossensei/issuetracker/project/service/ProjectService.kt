package io.github.rossensei.issuetracker.project.service

import io.github.rossensei.issuetracker.project.dto.request.StoreProjectRequest
import io.github.rossensei.issuetracker.project.dto.request.UpdateProjectRequest
import io.github.rossensei.issuetracker.project.dto.response.PageResponse
import io.github.rossensei.issuetracker.project.dto.response.ProjectResponse
import io.github.rossensei.issuetracker.project.entity.Project
import io.github.rossensei.issuetracker.project.repository.ProjectRepository
import io.github.rossensei.issuetracker.projectmember.entity.ProjectMember
import io.github.rossensei.issuetracker.projectmember.enums.ProjectMemberRole
import io.github.rossensei.issuetracker.projectmember.repository.ProjectMemberRepository
import io.github.rossensei.issuetracker.user.repository.UserRepository
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
    private val userRepository: UserRepository,
    private val projectMemberRepository: ProjectMemberRepository,
) {
    fun getMyProjects(userId: UUID, pageable: Pageable): PageResponse<ProjectResponse> =
        projectRepository
            .findAccessibleProjects(userId, pageable)
            .map { project ->
                val projectOwner = projectMemberRepository
                    .findByProjectIdAndRole(
                        project.id,
                        ProjectMemberRole.OWNER
                    )
                    ?: throw ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Project owner of project ID ${project.id} not found"
                    )

                ProjectResponse(
                    id = project.id,
                    name = project.name,
                    description = project.description,
                    owner = projectOwner.user.toResponse(),
                    createdAt = project.createdAt,
                    updatedAt = project.updatedAt,
                )
            }
            .let { page ->
                PageResponse(
                    content = page.content,
                    page = page.number,
                    size = page.size,
                    totalElements = page.totalElements,
                    totalPages = page.totalPages
                )
            }



    fun getAllProjects(userId: UUID, pageable: Pageable): PageResponse<ProjectResponse> =
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

    @Transactional
    fun storeProject(userId: UUID, request: StoreProjectRequest): ProjectResponse {
        val user = userRepository
            .findById(userId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User with ID $userId does not exist"
                )
            }

        val project = projectRepository.save(
                Project(
                    name = request.name,
                    description = request.description
                )
            )

        // add the user who created the project to the project member as OWNER
        projectMemberRepository.save(
            ProjectMember(
                project = project,
                user = user,
                role = ProjectMemberRole.OWNER
            )
        )

        return project.toResponse()
    }

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