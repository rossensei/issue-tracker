package io.github.rossensei.issuetracker.auth.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.UUID

class AppUser(
    val id: UUID,
    username: String,
    password: String,
    authorities: Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_USER")),
) : User(username, password, authorities)
