package io.github.rossensei.issuetracker.comment.service

import io.github.rossensei.issuetracker.comment.dto.request.StoreCommentRequest
import io.github.rossensei.issuetracker.comment.dto.request.UpdateCommentRequest
import io.github.rossensei.issuetracker.comment.dto.response.CommentResponse
import io.github.rossensei.issuetracker.comment.entity.Comment
import io.github.rossensei.issuetracker.comment.repository.CommentRepository
import io.github.rossensei.issuetracker.issue.repository.IssueRepository
import io.github.rossensei.issuetracker.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueRepository: IssueRepository,
    private val userRepository: UserRepository
) {
    fun getIssueComments(issueId: UUID): List<CommentResponse> {
        if (!issueRepository.existsById(issueId)) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "There is no issue with id $issueId"
            )
        }

        return commentRepository
            .findAllByIssueId(issueId)
            .map { it.toResponse() }
    }

    @Transactional
    fun storeComment(
        issueId: UUID,
        request: StoreCommentRequest
    ): CommentResponse {
        val issue = issueRepository
            .findById(issueId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "There is no issue with id $issueId"
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

        return commentRepository
            .save(
                Comment(
                    issue = issue,
                    user = user,
                    content = request.content
                )
            ).toResponse()
    }

    @Transactional
    fun updateComment(
        issueId: UUID,
        commentId: UUID,
        request: UpdateCommentRequest
    ): CommentResponse {
        val comment = commentRepository
            .findByIdAndIssueId(commentId, issueId)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "There is no comment with id $commentId"
            )

        comment.content = request.content

        return commentRepository
            .save(comment)
            .toResponse()
    }

    @Transactional
    fun deleteComment(
        issueId: UUID,
        commentId: UUID
    ) {
        val comment = commentRepository
            .findByIdAndIssueId(commentId, issueId)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Comment with ID $commentId from issue ID $issueId not found"
            )

        commentRepository.delete(comment)
    }
}