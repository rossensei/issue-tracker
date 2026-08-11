package io.github.rossensei.issuetracker.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.frontend")
class FrontendProperties(
    val baseUrl: String,
)