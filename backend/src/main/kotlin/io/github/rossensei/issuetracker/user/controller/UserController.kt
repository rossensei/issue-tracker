package io.github.rossensei.issuetracker.user.controller

import io.github.rossensei.issuetracker.user.dto.request.StoreUserRequest
import io.github.rossensei.issuetracker.user.dto.response.UserResponse
import io.github.rossensei.issuetracker.user.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "user", description = "User API")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/users")
    fun getAllUsers(): List<UserResponse> = userService.getAllUsers()

    @PostMapping("/users")
    fun storeUser(@Valid @RequestBody request: StoreUserRequest): UserResponse =
        userService.storeUser(request)
}