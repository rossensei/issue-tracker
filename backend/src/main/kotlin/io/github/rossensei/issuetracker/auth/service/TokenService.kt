package io.github.rossensei.issuetracker.auth.service

import io.github.rossensei.issuetracker.auth.config.AuthenticatedUser
import io.github.rossensei.issuetracker.auth.config.JwtProperties
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TokenService(
    private val encoder: JwtEncoder,
    private val jwtProperties: JwtProperties,
) {
    fun issue(user: AuthenticatedUser): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            // TODO: Replace with application name from application.properties
            .issuer("issue-tracker")
            .subject(user.id.toString())
            .issuedAt(now)
            .expiresAt(now.plus(jwtProperties.ttl))
            .claim("user", mapOf(
                "id" to user.id.toString(),
                "email" to user.email,
                "username" to user.username
            ))
            .build()
        val header = JwsHeader.with(MacAlgorithm.HS256).build()
        return encoder.encode(JwtEncoderParameters.from(header, claims)).tokenValue
    }
}
