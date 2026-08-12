package io.github.rossensei.issuetracker.projectmember.entity

import io.github.rossensei.issuetracker.project.entity.Project
import io.github.rossensei.issuetracker.projectmember.dto.response.ProjectMemberResponse
import io.github.rossensei.issuetracker.projectmember.enums.ProjectMemberRole
import io.github.rossensei.issuetracker.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(
    name = "project_members",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uq_project_member",
            columnNames = ["project_id", "user_id"]
        )
    ]
)
data class ProjectMember(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    val project: Project,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(
        name = "role",
        nullable = false,
        columnDefinition = "project_member_role"
    )
    var role: ProjectMemberRole = ProjectMemberRole.MEMBER,
    val joinedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun toResponse(): ProjectMemberResponse =
        ProjectMemberResponse(
            id = this.id ?: UUID.randomUUID(),
            project = this.project,
            user = this.user,
            role = this.role,
            joinedAt = this.joinedAt,
        )
}
