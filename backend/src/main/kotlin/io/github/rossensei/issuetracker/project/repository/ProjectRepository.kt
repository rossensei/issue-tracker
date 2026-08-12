package io.github.rossensei.issuetracker.project.repository

import io.github.rossensei.issuetracker.project.entity.Project
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ProjectRepository: JpaRepository<Project, UUID> {
    @Query(
        """
        SELECT DISTINCT p
        FROM Project p
        JOIN ProjectMember pm ON pm.project = p
        WHERE pm.user.id = :userId
        """
    )
    fun findAccessibleProjects(
        userId: UUID,
        pageable: Pageable
    ): Page<Project>
}