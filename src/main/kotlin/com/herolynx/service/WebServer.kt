package com.herolynx.service

import com.herolynx.service.conversions.ObjectMapperProvider
import com.herolynx.service.monitoring.HealthProbesRestService
import com.herolynx.service.monitoring.info
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.servlet.ServletContainer

internal class WebServer {

    private var jettyServer: Server? = null

    @Throws(Exception::class)
    fun start(port: Int = 8080, vararg restControllers: Any = arrayOf()) {
        info("*********** ")
        info("Starting web server - port: $port, controllers: ${restControllers.map { c -> c.javaClass.name }.reduce { s1, s2 -> "$s1,$s2" }}")

        val servletContextHandler = ServletContextHandler(ServletContextHandler.NO_SESSIONS)

        val jettyServer = Server(port)
        jettyServer.handler = servletContextHandler

        val rc = ResourceConfig()
                .register(JacksonFeature::class.java)
                .register(ObjectMapperProvider::class.java)
        restControllers.forEach { c -> rc.register(c) }

        val jerseyServletHolder = servletContextHandler.addServlet(ServletContainer::class.java, "/*")
        jerseyServletHolder.initOrder = 0

        val jerseyServletParams = mutableMapOf<String, String>()
        jerseyServletParams.put("jersey.config.server.provider.packages", HealthProbesRestService::class.java.`package`.name)

        jerseyServletHolder.initParameters = jerseyServletParams

        jettyServer.start()

    }

    @Throws(Exception::class)
    fun stop() {
        jettyServer?.stop()
    }

}
