package io.terminus.dice.test.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.terminus.dice.test.User
import org.apache.rocketmq.client.producer.DefaultMQProducer
import org.apache.rocketmq.common.message.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/rocketmq")
class RocketMQController {

    @Autowired
    private lateinit var producer: DefaultMQProducer

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @PostMapping("/produce")
    fun produce() {
        val user = User().apply {
            name = "dice-test"
            age = 18
        }
        val message = Message("dice-test", "dice-test", objectMapper.writeValueAsBytes(user))
        val result = producer.send(message)
        println(">>>result.status ${result.sendStatus}<<<")
        println(">>>result.json = ${objectMapper.writeValueAsString(result)}")
    }

}