package io.github.rossensei.issuetracker.comment.entity

import io.github.rossensei.issuetracker.comment.dto.response.CommentResponse
import io.github.rossensei.issuetracker.issue.entity.Issue
import io.github.rossensei.issuetracker.user.entity.User
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "comments")
class Comment(
    @Id
    @GeneratedValue(GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    val issue: Issue,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    var content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun toResponse(): CommentResponse =
        CommentResponse(
            id = this.id,
            issue = this.issue.toResponse(),
            user = this.user.toResponse(),
            content = this.content,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
}