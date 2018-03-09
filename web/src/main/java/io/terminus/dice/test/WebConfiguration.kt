package io.terminus.dice.test

import com.fasterxml.jackson.databind.ObjectMapper
import io.terminus.common.utils.JsonMapper
import io.terminus.dice.test.client.AddonPlatformClient
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.apache.rocketmq.client.producer.DefaultMQProducer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

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


    @Bean
    fun okHttpConnectionPool(): ConnectionPool {
        return ConnectionPool(0, 1, TimeUnit.MINUTES)
    }

    @Bean
    fun addonClient(objectMapper: ObjectMapper, connectionPool: ConnectionPool): AddonPlatformClient {
        val client = OkHttpClient().newBuilder()
                .connectionPool(connectionPool)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder().client(client)
                .baseUrl("http://localhost:8080")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
        return retrofit.create<AddonPlatformClient>(AddonPlatformClient::class.java)

    }
}