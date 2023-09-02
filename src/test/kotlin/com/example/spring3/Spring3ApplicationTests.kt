package com.example.spring3

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate

@SpringBootTest
class Spring3ApplicationTests {

    @Test
    fun contextLoads() {
        val restTemplate = RestTemplate()
        restTemplate.postForEntity("http://localhost:8080/users")
        restTemplate.getForEntity("http://localhost:8080/users/id")
    }

}
