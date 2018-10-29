package io.terminus.dice.test.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.springframework.web.client.RestTemplate

class RestTest {
    val restTemplate = RestTemplate()

    @Test
    fun testMapJson(){
        val test = restTemplate.getForObject("http://localhost:8080/api/v2/map/json", Map::class.java) as Map<String, Map<String, String>>
        println(ObjectMapper().writeValueAsString(test))
    }
}