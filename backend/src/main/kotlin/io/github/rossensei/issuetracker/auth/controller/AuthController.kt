package io.github.rossensei.issuetracker.auth.controller

import io.github.rossensei.issuetracker.auth.dto.LoginRequest
import io.github.rossensei.issuetracker.auth.dto.RegisterRequest
import io.github.rossensei.issuetracker.auth.config.AppUser
import io.github.rossensei.issuetracker.auth.config.AuthenticatedUser
import io.github.rossensei.issuetracker.auth.config.JwtProperties
import io.github.rossensei.issuetracker.auth.service.RegistrationService
import io.github.rossensei.issuetracker.auth.service.TokenService
import io.github.rossensei.issuetracker.user.dto.response.UserResponse
import io.github.rossensei.issuetracker.user.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.Duration

@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth", description = "Auth API")
class AuthController(
    private val authManager: AuthenticationManager,
    private val token: TokenService,
    private val jwtProperties: JwtProperties,
    private val userService: UserService,
    private val registrationService: RegistrationService
) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthenticatedUser> {
        val auth = authManager.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(
                request.username.trim().lowercase(),
                request.password
            )
        )

        val principal = auth.principal as AppUser
        val dbUser = userService.getUserById(principal.id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val user = AuthenticatedUser(
            id = dbUser.id,
            email = dbUser.email,
            username = dbUser.username,
        )

        val cookie = buildCookie(token.issue(user), jwtProperties.ttl).toString()
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie)
            .body(user)
    }

    @DeleteMapping
    fun logout(): ResponseEntity<Void> =
        ResponseEntity.noContent()
            .header(HttpHeaders.SET_COOKIE, buildCookie("", Duration.ZERO).toString())
            .build()

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal user: AuthenticatedUser): AuthenticatedUser = user

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthenticatedUser> {
        val user = registrationService.register(request)

        return ResponseEntity.status(HttpStatus.CREATED)
            .header(
                HttpHeaders.SET_COOKIE,
                buildCookie(
                    token.issue(user),
                    jwtProperties.ttl
                ).toString())
            .build()
    }

    private fun buildCookie(value: String, maxAge: Duration) =
        ResponseCookie.from(jwtProperties.cookieName, value)
            .httpOnly(true)
            .secure(jwtProperties.secureCookie)
            .sameSite("Strict")
            .path("/")
            .maxAge(maxAge)
            .build()
}
