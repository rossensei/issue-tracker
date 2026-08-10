package io.github.rossensei.issuetracker.projectmember.service

import io.github.rossensei.issuetracker.project.repository.ProjectRepository
import io.github.rossensei.issuetracker.projectmember.dto.request.StoreProjectMemberRequest
import io.github.rossensei.issuetracker.projectmember.dto.request.UpdateProjectMemberRequest
import io.github.rossensei.issuetracker.projectmember.dto.response.ProjectMemberResponse
import io.github.rossensei.issuetracker.projectmember.entity.ProjectMember
import io.github.rossensei.issuetracker.projectmember.enums.ProjectMemberRole
import io.github.rossensei.issuetracker.projectmember.repository.ProjectMemberRepository
import io.github.rossensei.issuetracker.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class ProjectMemberService(
    private val projectMemberRepository: ProjectMemberRepository,
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository,
) {
    fun getProjectMembers(projectId: UUID): List<ProjectMemberResponse> {
        if (!projectRepository.existsById(projectId)) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Project with ID $projectId does not exist"
            )
        }

        return projectMemberRepository
            .findAllByProjectId(projectId)
            .map { it.toResponse() }
    }

    @Transactional
    fun addProjectMember(
        projectId: UUID,
        request: StoreProjectMemberRequest
    ): ProjectMemberResponse {
        val project = projectRepository
            .findById(projectId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Project with ID $projectId does not exist"
                )
            }

        val user = userRepository
            .findById(request.userId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User with ID ${request.userId} does not exist"
                )
            }

        return projectMemberRepository.save(
            ProjectMember(
                project = project,
                user = user,
            )
        ).toResponse()
    }

    @Transactional
    fun updateProjectMember(
        projectId: UUID,
        userId: UUID,
        request: UpdateProjectMemberRequest
    ): ProjectMemberResponse {
        val projectMember = projectMemberRepository
            .findByProjectIdAndUserId(projectId, userId)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Project member with project ID $projectId user ID $userId does not exist"
            )

        projectMember.role = request.role

        return projectMember.toResponse()
    }

    @Transactional
    fun removeProjectMember(
        projectId: UUID,
        userId: UUID,
    ) {
        val projectMember = projectMemberRepository
            .findByProjectIdAndUserId(projectId, userId)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Project member with project ID $projectId user ID $userId does not exist"
            )

        if (projectMember.role == ProjectMemberRole.OWNER) {
            val ownerCount = projectMemberRepository
                .countByProjectIdAndRole(
                    projectId,
                    ProjectMemberRole.OWNER
                )

            if (ownerCount <= 1) {
                throw IllegalStateException(
                    "Cannot remove the only project owner"
                )
            }
        }
        projectMemberRepository.delete(projectMember)
    }
}