package com.awe.alpha.persistence.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Account @JsonCreator constructor(
        @JsonProperty("username")
        val username: String,
        @JsonProperty("password")
        val password: String,
        @JsonProperty("full_name")
        val fullName: String
)