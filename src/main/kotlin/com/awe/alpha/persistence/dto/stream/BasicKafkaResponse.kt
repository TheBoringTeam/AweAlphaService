package com.awe.alpha.persistence.dto.stream

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

class BasicKafkaResponse @JsonCreator constructor(
        @JsonProperty("message")
        val message: String,

        @JsonProperty("status_code")
        val statusCode: HttpStatus
)