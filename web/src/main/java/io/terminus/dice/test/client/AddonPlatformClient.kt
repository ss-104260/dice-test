package io.terminus.dice.test.client

import io.terminus.dice.test.User
import retrofit2.Call
import retrofit2.http.*

interface AddonPlatformClient {

    @GET("/api/test/user")
    fun justTest(): Call<User>

}