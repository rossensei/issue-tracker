package io.github.rossensei.issuetracker.issue.controller

import io.github.rossensei.issuetracker.issue.dto.request.StoreIssueRequest
import io.github.rossensei.issuetracker.issue.dto.request.UpdateIssueRequest
import io.github.rossensei.issuetracker.issue.dto.response.IssueResponse
import io.github.rossensei.issuetracker.issue.service.IssueService
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
@RequestMapping("/api")
class IssueController(
    private val issueService: IssueService,
) {
    // TODO: Remove later after auth is implemented
    val CREATED_BY_ID = UUID.fromString("d08e567b-aeae-4a9d-b2f5-6c7edb4d2cae")

    @GetMapping("/projects/{projectId}/issues")
    fun index(@PathVariable projectId: UUID): List<IssueResponse> =
        issueService.getProjectIssues(projectId)

    @PostMapping("/projects/{projectId}/issues")
    fun store(
        @PathVariable projectId: UUID,
        @Valid @RequestBody request: StoreIssueRequest,
    ) = issueService.storeProjectIssue(projectId, CREATED_BY_ID, request)

    @GetMapping("/issues/{issueId}")
    fun show(@PathVariable issueId: UUID, ): IssueResponse =
        issueService.getIssue(issueId)

    @PatchMapping("/issues/{issueId}")
    fun update(
        @PathVariable issueId: UUID,
        @Valid @RequestBody request: UpdateIssueRequest,
    ): IssueResponse =
        issueService.updateIssue(issueId, request)

    @DeleteMapping("/issues/{issueId}")
    fun destroy(@PathVariable issueId: UUID) =
        issueService.deleteIssue(issueId)
}