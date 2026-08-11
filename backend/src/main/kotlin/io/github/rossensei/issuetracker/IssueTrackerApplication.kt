package io.github.rossensei.issuetracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class IssueTrackerApplication

fun main(args: Array<String>) {
	runApplication<IssueTrackerApplication>(*args)
}
