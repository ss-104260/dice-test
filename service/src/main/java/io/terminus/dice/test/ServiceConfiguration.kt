package io.terminus.dice.test

import com.fasterxml.jackson.databind.ObjectMapper
import io.terminus.common.utils.JsonMapper
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer
import org.apache.rocketmq.client.consumer.MessageSelector
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently
import org.apache.rocketmq.common.consumer.ConsumeFromWhere
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {
    @Value("\${terminus.rocketmq.nameServerAddress}")
    private lateinit var nameServAddr: String

    @Value("\${terminus.rocketmq.consumerGroup}")
    private lateinit var consumerGroup: String

    @Bean
    fun rocketConsumer(): DefaultMQPushConsumer {
        val consumer = DefaultMQPushConsumer(consumerGroup)
        consumer.namesrvAddr = nameServAddr
        consumer.subscribe("dice-test", MessageSelector.byTag("dice-test"))
        consumer.consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET
        consumer.registerMessageListener(MessageListenerConcurrently { mutableList, _ ->
            println(">>>${mutableList[0]}<<<")
            mutableList.forEach{
                println(">>>${objectMapper().readValue(it.body, User::class.java)}<<<")
            }
            ConsumeConcurrentlyStatus.CONSUME_SUCCESS
        })
        consumer.start()
        return consumer
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return JsonMapper.JSON_NON_DEFAULT_MAPPER.mapper
    }


}
