package io.github.rossensei.issuetracker.config

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class UserAuthenticationToken(
    private val user: AuthenticatedUser,
    val jwt: Jwt,
    authorities: Collection<GrantedAuthority> = emptyList(),
): AbstractAuthenticationToken(authorities) {
    init {
        isAuthenticated = true
    }

    override fun getPrincipal(): AuthenticatedUser = user
    override fun getCredentials(): Jwt = jwt
    override fun getName(): String = user.id.toString()
}
