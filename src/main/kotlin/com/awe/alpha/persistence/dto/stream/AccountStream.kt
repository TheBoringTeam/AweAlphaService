package com.awe.alpha.persistence.dto.stream

import com.awe.alpha.persistence.dto.request.AccountSignUpForm
import com.fasterxml.jackson.annotation.JsonProperty

class AccountStream {
    constructor(signUpForm: AccountSignUpForm) {
        this.username = signUpForm.username
        this.email = signUpForm.email
        this.password = signUpForm.password
        this.isCollective = signUpForm.isCollective
    }

    constructor(username: String, password: String, email: String, isCollective: Boolean) {
        this.username = username
        this.password = password
        this.email = email
        this.isCollective = isCollective
    }

    @JsonProperty("username")
    var username: String

    @JsonProperty("password")
    var password: String

    @JsonProperty("email")
    var email: String

    @JsonProperty("is_collective")
    var isCollective: Boolean = false
}