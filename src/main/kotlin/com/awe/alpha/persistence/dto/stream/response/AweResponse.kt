package com.awe.alpha.persistence.dto.stream.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Class represents response from microservice to edge service.
 * @property value the value of response. Ex: AccountObject or ErrorObject
 * @property isSuccessful the flag shows if it's a successful message or shit (error)
 */
class AweResponse(
        @JsonProperty("value")
        val value: String,

        @param:JsonProperty("is_successful")
        val isSuccessful: Boolean
) {
}