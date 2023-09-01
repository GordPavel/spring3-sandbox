package com.example.spring3.config.r2dbc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.data.r2dbc.dialect.PostgresDialect.INSTANCE as PostgresDialect

fun interface R2dbcCustomConverter<FROM, TO> : Converter<FROM, TO>

open class R2dbcCustomConverterAdapter<FROM : Any, TO>(
    private val delegate: Converter<FROM, TO>
) : R2dbcCustomConverter<FROM, TO> {
    override fun convert(source: FROM): TO? = delegate.convert(source)
}

@Configuration
@EnableR2dbcRepositories(
    "com.example.spring3.user.dao",
)
class R2dbcConfiguration {
    @Bean
    fun customR2dbcConversions(
        converters: Collection<R2dbcCustomConverter<*, *>>,
    ): R2dbcCustomConversions = R2dbcCustomConversions.of(
        PostgresDialect,
        *converters.toTypedArray(),
    )
}

@Configuration
class R2dbcJacksonConfiguration {
    @Bean("postgresObjectMapper")
    fun postgresObjectMapper(): ObjectMapper = jacksonObjectMapper()
}