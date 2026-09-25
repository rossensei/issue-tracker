package io.github.rossensei.issuetracker.auth.exception

class DuplicateUserException(val field: String): RuntimeException("$field already taken")
