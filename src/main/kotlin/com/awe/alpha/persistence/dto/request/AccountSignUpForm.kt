package com.awe.alpha.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.*


class AccountSignUpForm @JsonCreator constructor(
        // TODO: Add unique username check
        @field:JsonProperty("username")
        @field:NotNull(message = "Username could not be empty")
//        @field:UniqueUsername
        @Size(message = "Login should be from 6 to 32 length", min = 5, max = 32)
        @field:Pattern(regexp = "^(?=[a-zA-Z0-9._])(?!.*[_.]{2})[^_.].*[^_.]\$",
                message = "Username format is not correct")
        private val usernameField: String?,

        @field:JsonProperty("password")
        @field:NotEmpty(message = "Password could not be empty")
        @field:Size(message = "Password should be from 6 to 32 length", min = 6, max = 32)
        private val passwordField: String?,

        // TODO: Add unique username check
        @field:JsonProperty("email")
        @field:NotEmpty(message = "Email could not be empty")
        @field:Email(message = "Email should be properly formatted")
//        @field:UniqueEmail(message = "User with this email already exists")
        private val emailField: String?,

        @field:JsonProperty("is_collective")
        @field:NotNull(message = "Collective could not be null")
        private val isCollectiveField: Boolean?
) {
    val username: String
        get() = usernameField!!

    val password: String
        get() = passwordField!!

    val isCollective: Boolean
        get() = isCollectiveField!!

    val email: String
        get() = emailField!!
}