package com.herolynx.service

import com.herolynx.service.conversions.ObjectMapperProvider
import com.herolynx.service.monitoring.info
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI
import io.netty.channel.Channel

internal class WebServer {

    private var server: Channel? = null

    @Throws(Exception::class)
    fun start(baseUri: URI = URI.create("http://localhost:8080")) {
        val pkgName = javaClass.`package`.name;
        info("Starting web server - uri: $baseUri, root package: $pkgName")
        val serverConfig = ResourceConfig()
                .register(JacksonFeature::class.java)
                .register(ObjectMapperProvider::class.java)
                .packages(pkgName)
        server = NettyHttpContainerProvider.createHttp2Server(baseUri, serverConfig, null)
        Thread.currentThread().join()
    }

    @Throws(Exception::class)
    fun stop() {
        info("Stopping web server")
        server?.close()
    }

}
