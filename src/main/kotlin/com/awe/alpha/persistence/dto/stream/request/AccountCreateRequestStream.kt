package com.awe.alpha.persistence.dto.stream.request

import com.awe.alpha.persistence.dto.request.AccountSignUpForm
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

class AccountCreateRequestStream @JsonCreator constructor(signUpForm: AccountSignUpForm) {

    @field:JsonProperty("username")
    var username: String = signUpForm.username

    @field:JsonProperty("password")
    var password: String = signUpForm.password

    @field:JsonProperty("email")
    var email: String = signUpForm.email

    @get:JsonProperty("is_collective")
    var isCollective: Boolean = signUpForm.isCollective
}