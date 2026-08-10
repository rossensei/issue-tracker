package io.github.rossensei.issuetracker.comment.controller

import io.github.rossensei.issuetracker.comment.dto.request.StoreCommentRequest
import io.github.rossensei.issuetracker.comment.dto.request.UpdateCommentRequest
import io.github.rossensei.issuetracker.comment.dto.response.CommentResponse
import io.github.rossensei.issuetracker.comment.service.CommentService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/issues/{issueId}")
class CommentController(
    private val commentService: CommentService
) {
    @GetMapping("/comments")
    fun getComments(@PathVariable issueId: UUID): List<CommentResponse> =
        commentService.getIssueComments(issueId)

    @PostMapping("/comments")
    fun storeComment(
        @PathVariable issueId: UUID,
        @Valid @RequestBody request: StoreCommentRequest
    ): CommentResponse =
        commentService.storeComment(issueId, request)

    @PatchMapping("/comments/{commentId}")
    fun updateComment(
        @PathVariable issueId: UUID,
        @PathVariable commentId: UUID,
        @Valid @RequestBody request: UpdateCommentRequest
    ): CommentResponse =
        commentService.updateComment(
            issueId,
            commentId,
            request
        )

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @PathVariable issueId: UUID,
        @PathVariable commentId: UUID
    ) = commentService.deleteComment(issueId, commentId)
}