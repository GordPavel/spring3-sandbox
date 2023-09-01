package com.example.spring3.test

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {
    @GetMapping("/hello", produces = [APPLICATION_JSON_VALUE])
    suspend fun hello(): Map<String, Any> = mapOf<String, Any>(
        "text" to "Hello world",
    )
}