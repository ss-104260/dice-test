package io.terminus.dice.test

import com.fasterxml.jackson.databind.ObjectMapper
import io.terminus.common.utils.JsonMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return JsonMapper.JSON_NON_DEFAULT_MAPPER.mapper
    }




}