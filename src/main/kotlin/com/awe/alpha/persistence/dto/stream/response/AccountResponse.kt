package com.awe.alpha.persistence.dto.stream.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


class AccountResponse @JsonCreator constructor(
        @JsonProperty("username")
        var username: String,

        @JsonProperty("email")
        val email: String,

        @JsonProperty("full_name")
        val name: String,

        @param:JsonProperty("is_collective")
        val isCollective: Boolean,

        @JsonProperty("uuid")
        val uuid: String,

        @JsonProperty("followers")
        val followers: Long,

        @param:JsonProperty("is_activated")
        val isActivated: Boolean,

        @param:JsonProperty("is_verified")
        val isVerified: Boolean,

        @param:JsonProperty("is_blocked")
        val isBlocked: Boolean,

        @JsonProperty("permissions")
        val permissions: List<String>
) {}