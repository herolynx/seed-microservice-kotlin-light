package com.herolynx.service.conversions

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.Provider

@Provider
class ObjectMapperProvider : ContextResolver<ObjectMapper> {

    val objectMapper = ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT) // pretty-print
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // use iso-8601 for dates
            .registerModule(KotlinModule()) // this doesn't seem to be required to serialize our data class, but it is required to deserialize

    override fun getContext(type: Class<*>?): ObjectMapper? = objectMapper
}