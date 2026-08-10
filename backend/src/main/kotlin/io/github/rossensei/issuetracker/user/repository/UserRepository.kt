package io.github.rossensei.issuetracker.user.repository

import io.github.rossensei.issuetracker.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID>