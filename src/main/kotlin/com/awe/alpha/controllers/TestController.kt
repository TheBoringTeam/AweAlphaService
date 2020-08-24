package com.awe.alpha.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/test")
class TestController {
    @GetMapping("/test")
    @ResponseBody
    fun testSendAndReceiveValue(): ResponseEntity<String> {

        return ResponseEntity.ok("OK")
    }
}