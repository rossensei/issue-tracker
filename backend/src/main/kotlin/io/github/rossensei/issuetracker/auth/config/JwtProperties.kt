package io.github.rossensei.issuetracker.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("app.jwt")
data class JwtProperties(
    val secret: String,
    val ttl: Duration = Duration.ofMinutes(15),
    val cookieName: String = "access_token",
    val secureCookie: Boolean = true,
)