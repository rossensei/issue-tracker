package io.github.rossensei.issuetracker.project.repository

import io.github.rossensei.issuetracker.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProjectRepository: JpaRepository<Project, UUID>