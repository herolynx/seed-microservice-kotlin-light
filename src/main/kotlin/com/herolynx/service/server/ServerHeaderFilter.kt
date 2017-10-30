package com.herolynx.service.server

import javax.annotation.Priority
import javax.ws.rs.container.*
import javax.ws.rs.ext.Provider

@Provider
@Priority(10000)
class ServerHeaderFilter : ContainerResponseFilter {

    override fun filter(requestContext: ContainerRequestContext?, responseContext: ContainerResponseContext?) {
        responseContext?.headers?.add(HEADER_SERVER, "n/a")
    }

    companion object {

        private val HEADER_SERVER = "Server"

    }

}