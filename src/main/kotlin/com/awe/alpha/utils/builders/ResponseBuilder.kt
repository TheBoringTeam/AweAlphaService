package com.awe.alpha.utils.builders

import org.springframework.http.ResponseEntity

class ResponseBuilder {
    private val _responseMap = HashMap<String, Any>()

    fun addField(field: String, value: Any) = apply {
        this._responseMap[field] = value
    }

    fun toJSON(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(_responseMap)
    }
}