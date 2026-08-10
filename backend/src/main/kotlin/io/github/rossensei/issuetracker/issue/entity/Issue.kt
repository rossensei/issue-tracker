package io.github.rossensei.issuetracker.issue.entity

import io.github.rossensei.issuetracker.issue.dto.response.IssueResponse
import io.github.rossensei.issuetracker.issue.enums.IssuePriority
import io.github.rossensei.issuetracker.issue.enums.IssueStatus
import io.github.rossensei.issuetracker.project.entity.Project
import io.github.rossensei.issuetracker.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "issues")
class Issue(
    @Id
    val id: UUID = UUID.randomUUID(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    val project: Project,
    var title: String = "",
    var description: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: IssueStatus = IssueStatus.TODO,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var priority: IssuePriority = IssuePriority.MEDIUM,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    val createdBy: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", foreignKey = ForeignKey(name = "fk_issue_assignee"))
    var assignedTo: User? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun toResponse(): IssueResponse =
        IssueResponse(
            id = this.id,
            project = this.project.toResponse(),
            title = this.title,
            description = this.description,
            status = this.status,
            priority = this.priority,
            createdBy = this.createdBy.toResponse(),
            assignedTo = this.assignedTo?.toResponse(),
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
}