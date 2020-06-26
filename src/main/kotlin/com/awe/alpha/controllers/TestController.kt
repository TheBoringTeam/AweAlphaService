package com.awe.alpha.controllers

import com.awe.alpha.services.AlphaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController("/api/test")
class TestController {

    @Autowired
    private lateinit var _alphaService: AlphaService


    @GetMapping("/test-doubly-linked-response")
    @ResponseBody
    fun testSendAndReceiveValue(): ResponseEntity<String> {
        val response = _alphaService.sendResponseEvent()
        return ResponseEntity.ok(response)
    }
}