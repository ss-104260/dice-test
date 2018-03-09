package io.terminus.dice.test.controller

import io.terminus.dice.test.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2")
class TestController {
    @GetMapping("/addons/all")
    fun test(): ResponseEntity<Void> {
//        Thread.sleep(1000*30)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}