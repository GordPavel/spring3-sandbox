package com.example.spring3.config.r2dbc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.r2dbc.postgresql.codec.Json
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component

@WritingConverter
@Component
class MapToJsonConverter(
    @Qualifier("postgresObjectMapper")
    private val postgresObjectMapper: ObjectMapper,
) : R2dbcCustomConverterAdapter<Map<String, *>, Json>(
    Converter<Map<String, *>, String>(postgresObjectMapper::writeValueAsString).andThen(Json::of)
)

@ReadingConverter
@Component
class JsonToMapConverter(
    @Qualifier("postgresObjectMapper")
    private val postgresObjectMapper: ObjectMapper,
) : R2dbcCustomConverterAdapter<Json, Map<String, *>>(
    Converter(Json::asString).andThen(postgresObjectMapper::readValue)
)

@WritingConverter
@Component
class CollectionToJsonConverter(
    @Qualifier("postgresObjectMapper")
    private val postgresObjectMapper: ObjectMapper,
) : R2dbcCustomConverterAdapter<Collection<*>, Json>(
    Converter<Collection<*>, String>(postgresObjectMapper::writeValueAsString).andThen(Json::of)
)

@ReadingConverter
@Component
class JsonToCollectionConverter(
    @Qualifier("postgresObjectMapper")
    private val postgresObjectMapper: ObjectMapper,
) : R2dbcCustomConverterAdapter<Json, Set<*>>(
    Converter(Json::asString).andThen { postgresObjectMapper.readValue(it) }
)
