package io.terminus.dice.test.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.terminus.dice.test.User
import io.terminus.dice.test.client.AddonPlatformClient
import org.apache.rocketmq.client.producer.DefaultMQProducer
import org.apache.rocketmq.common.message.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.Call
import java.lang.RuntimeException
import java.util.*


@RestController
@RequestMapping("/api/rocketmq")
class RocketMQController {

    @Autowired
    private lateinit var producer: DefaultMQProducer

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var addonClient: AddonPlatformClient


    @PostMapping("/produce")
    fun produce() {
        val user = User().apply {
            name = "dice-test"
            age = Random().nextInt(100)
        }
        val message = Message("dice-test", "dice-test", objectMapper.writeValueAsBytes(user))
        val result = producer.send(message)
        println(">>>result.status ${result.sendStatus}<<<")
        println(">>>result.json = ${objectMapper.writeValueAsString(result)}")
    }

    @GetMapping("/httptest")
    fun test(): User {
        return addonClient.justTest().executeResp()
    }

    private fun <B> Call<B>.executeResp(): B = this.execute().let {
        if (it.isSuccessful) {
            return it.body() ?: throw RuntimeException("body null")
        }
        throw RuntimeException(it.errorBody()?.string())
    }


}