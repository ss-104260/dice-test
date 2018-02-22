package io.terminus.dice.test

import com.fasterxml.jackson.databind.ObjectMapper
import io.terminus.common.utils.JsonMapper
import org.apache.rocketmq.client.producer.DefaultMQProducer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebConfiguration {

    @Value("\${terminus.rocketmq.nameServerAddress}")
    private lateinit var nameServAddr: String

    @Value("\${terminus.rocketmq.producerGroup}")
    private lateinit var producerGroup: String


    @Bean
    fun defaultMQProducer(): DefaultMQProducer {
        val producer = DefaultMQProducer(producerGroup)
        producer.namesrvAddr = nameServAddr
        producer.start()
        return producer
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return JsonMapper.JSON_NON_DEFAULT_MAPPER.mapper
    }
}