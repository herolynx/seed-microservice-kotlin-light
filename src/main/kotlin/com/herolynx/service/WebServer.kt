package com.herolynx.service

import com.herolynx.service.conversions.ObjectMapperProvider
import com.herolynx.service.monitoring.info
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.servlet.ServletContainer

internal class WebServer {

    private var jettyServer: Server? = null

    @Throws(Exception::class)
    fun start(port: Int = 8080) {
        val pkgName = javaClass.`package`.name;
        info("Starting web server - port: $port, root package: $pkgName")
        val serverConfig = ResourceConfig()
                .register(JacksonFeature::class.java)
                .register(ObjectMapperProvider::class.java)
                .packages(pkgName)
        val servletHolder = createServletHolder(serverConfig)
        jettyServer = startServer(servletHolder, port)
    }

    private fun startServer(servletHolder: ServletHolder, port: Int): Server {
        val servletContextHandler = ServletContextHandler(ServletContextHandler.NO_SESSIONS)
        servletContextHandler.addServlet(servletHolder, "/*")
        val jettyServer = Server(port)
        jettyServer.handler = servletContextHandler
        jettyServer.start()
        jettyServer.join()
        return jettyServer
    }

    private fun createServletHolder(serverConfig: ResourceConfig): ServletHolder {
        val servletContainer = ServletContainer(serverConfig)
        val servletHolder = ServletHolder(servletContainer)
        servletHolder.initOrder = 0
        return servletHolder
    }

    @Throws(Exception::class)
    fun stop() {
        info("Stoppping web server")
        jettyServer?.stop()
    }

}
