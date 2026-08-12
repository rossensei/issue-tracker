package io.github.rossensei.issuetracker.projectmember.repository

import io.github.rossensei.issuetracker.projectmember.entity.ProjectMember
import io.github.rossensei.issuetracker.projectmember.enums.ProjectMemberRole
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProjectMemberRepository: JpaRepository<ProjectMember, UUID> {
    fun findAllByProjectId(projectId: UUID): List<ProjectMember>
    fun findByProjectIdAndUserId(projectId: UUID, userId: UUID): ProjectMember?
    fun findByProjectIdAndRole(projectId: UUID, role: ProjectMemberRole): ProjectMember?
    fun countByProjectIdAndRole(projectId: UUID, role: ProjectMemberRole): Long
}