package com.awe.alpha.persistence.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class BasicStringResponse(
        @JsonProperty(value = "message")
        var message: String
)