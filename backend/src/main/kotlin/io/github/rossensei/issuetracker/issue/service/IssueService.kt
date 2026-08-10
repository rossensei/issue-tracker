package io.github.rossensei.issuetracker.issue.service

import io.github.rossensei.issuetracker.issue.dto.request.StoreIssueRequest
import io.github.rossensei.issuetracker.issue.dto.request.UpdateIssueRequest
import io.github.rossensei.issuetracker.issue.dto.response.IssueResponse
import io.github.rossensei.issuetracker.issue.entity.Issue
import io.github.rossensei.issuetracker.issue.repository.IssueRepository
import io.github.rossensei.issuetracker.project.repository.ProjectRepository
import io.github.rossensei.issuetracker.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class IssueService(
    private val issueRepository: IssueRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
) {
    fun getProjectIssues(projectId: UUID): List<IssueResponse> {
        if (!projectRepository.existsById(projectId)) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Project with ID $projectId does not exist"
            )
        }

        return issueRepository
            .findAllByProjectId(projectId)
            .map {  it.toResponse() }
    }

    fun storeProjectIssue(
        projectId: UUID,
        createdById: UUID,
        request: StoreIssueRequest
    ): IssueResponse {
        val project = projectRepository
            .findById(projectId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Project with ID: $projectId does not exist"
                )
            }

        val createdBy = userRepository
            .findById(createdById)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User with ID: $createdById does not exist"
                )
            }
        val assignedTo = request.assignedTo?.let { userId ->
            userRepository
                .findById(userId)
                .orElseThrow {
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with ID: $userId does not exist"
                    )
                }
        }

        return issueRepository
            .save(
                Issue(
                    project = project,
                    title = request.title,
                    description = request.description,
                    priority = request.priority,
                    createdBy = createdBy,
                    assignedTo = assignedTo,
                )
            ).toResponse()
    }

    fun getIssue(issueId: UUID): IssueResponse =
        issueRepository
            .findById(issueId)
            .map { it.toResponse() }
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Issue with id $issueId not found"
                )
            }

    @Transactional
    fun updateIssue(issueId: UUID, request: UpdateIssueRequest): IssueResponse =
        issueRepository
            .findById(issueId)
            .map { issue ->
                issue.title = request.title
                issue.description = request.description
                issue.status = request.status
                issue.priority = request.priority

                issue.assignedTo = request.assignedTo?.let { userId ->
                    userRepository
                        .findById(userId)
                        .orElseThrow {
                            ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User with ID: $userId does not exist"
                            )
                        }

                }

                issueRepository.save(issue).toResponse()
            }
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Issue with ID: $issueId does not exist"
                )
            }

    @Transactional
    fun deleteIssue(issueId: UUID) = issueRepository.deleteById(issueId)
}