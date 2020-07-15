package com.awe.alpha.persistence.dto.response

import com.awe.alpha.persistence.dto.stream.response.AccountResponse
import com.fasterxml.jackson.annotation.JsonProperty

class AccountEntityResponse(account: AccountResponse) {
    @JsonProperty("username")
    val username = account.username

    @JsonProperty("full_name")
    val fullName = account.name

    @JsonProperty("uuid")
    val uuid = account.uuid

    @JsonProperty("email")
    val email = account.email

    @JsonProperty("followers")
    val followers = account.followers

    @get:JsonProperty("is_collective")
    val isCollective = account.isCollective

    @get:JsonProperty("is_activated")
    val isActivated = account.isActivated

    @get:JsonProperty("is_blocked")
    val isBlocked = account.isBlocked
}