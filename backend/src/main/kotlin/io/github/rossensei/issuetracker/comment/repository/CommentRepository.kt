package io.github.rossensei.issuetracker.comment.repository

import io.github.rossensei.issuetracker.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CommentRepository: JpaRepository<Comment, UUID> {
    fun findAllByIssueId(issueId: UUID): List<Comment>
    fun findByIdAndIssueId(id: UUID, issueId: UUID): Comment?
}