package io.github.rossensei.issuetracker.issue.repository

import io.github.rossensei.issuetracker.issue.entity.Issue
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IssueRepository: JpaRepository<Issue, UUID> {
    fun findAllByProjectId(projectId: UUID): List<Issue>
}