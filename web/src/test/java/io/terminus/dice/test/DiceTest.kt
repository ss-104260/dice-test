package io.terminus.dice.test

import io.terminus.common.utils.JsonMapper
import io.terminus.dice.test.client.AddonPlatformClient
import io.terminus.dice.test.client.interceptor.RetryInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class DiceTest {
    private lateinit var addonClient: AddonPlatformClient
    @Before
    fun before() {
        print("===")
        val client = OkHttpClient().newBuilder()
                .connectionPool(ConnectionPool(0, 1, TimeUnit.MINUTES))
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).addInterceptor(RetryInterceptor.Builder().build()).build()
        val retrofit = Retrofit.Builder().client(client)
                .baseUrl("http://localhost:8080")
                .addConverterFactory(JacksonConverterFactory.create(JsonMapper.JSON_NON_DEFAULT_MAPPER.mapper))
                .build()

        addonClient = retrofit.create<AddonPlatformClient>(AddonPlatformClient::class.java)
    }

    @Test
    fun testGet() {
        println(addonClient.justTest().execute())
    }
}