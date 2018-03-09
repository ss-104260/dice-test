package io.terminus.dice.test.controller

import io.terminus.dice.test.model.Student
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/student")
class StudentController {

    @RequestMapping("/kotlin/get")
    fun testKotlinGet(): Student {
        return Student().apply {
            name = "me"
        }
    }
}