package io.github.rossensei.issue_tracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IssueTrackerApplication

fun main(args: Array<String>) {
	runApplication<IssueTrackerApplication>(*args)
}
