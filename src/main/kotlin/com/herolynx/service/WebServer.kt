package com.herolynx.service

import com.herolynx.service.conversions.ObjectMapperProvider
import com.herolynx.service.monitoring.info
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.servlet.ServletContainer

internal class WebServer {

    private var jettyServer: Server? = null

    @Throws(Exception::class)
    fun start(port: Int = 8080) {
        info("*********** ")
        val pkgName = javaClass.`package`.name;
        info("Starting web server - port: $port, root package: $pkgName")

        val servletContextHandler = ServletContextHandler(ServletContextHandler.NO_SESSIONS)

        val jettyServer = Server(port)
        jettyServer.handler = servletContextHandler

        val rc = ResourceConfig()
                .register(JacksonFeature::class.java)
                .register(ObjectMapperProvider::class.java)

        val jerseyServletHolder = servletContextHandler.addServlet(ServletContainer::class.java, "/*")
        jerseyServletHolder.initOrder = 0

        val jerseyServletParams = mutableMapOf<String, String>()
        jerseyServletParams.put("jersey.config.server.provider.packages", pkgName)

        jerseyServletHolder.initParameters = jerseyServletParams

        jettyServer.start()

    }

    @Throws(Exception::class)
    fun stop() {
        jettyServer?.stop()
    }

}
