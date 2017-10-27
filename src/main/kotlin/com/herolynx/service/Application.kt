package com.herolynx.service

import com.herolynx.service.conversions.ObjectMapperProvider
import com.herolynx.service.monitoring.ProbesResource
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI

class Application : javax.ws.rs.core.Application() {

    override fun getSingletons(): MutableSet<Any> {
        return mutableSetOf(ProbesResource())
    }

    companion object {

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val uri = URI.create("http://localhost:8080/")
            consoleOut("Starting web service: ${uri.path}")
            val resourceConfig = ResourceConfig
                    .forApplication(Application())
                    .register(JacksonFeature::class.java)
                    .register(ObjectMapperProvider::class.java)
            val webServer = NettyHttpContainerProvider.createHttp2Server(uri, resourceConfig, null)
            Runtime.getRuntime().addShutdownHook(Thread(Runnable {
                consoleOut("Closing web service")
                webServer.close()
            }))
        }

        private fun consoleOut(msg: String) {
            System.out.println("********************************************")
            System.out.println("********    $msg")
            System.out.println("********************************************")
        }

        internal fun addShutdownHook(webService: WebServer) {
            Runtime.getRuntime().addShutdownHook(Thread {
                try {
                    consoleOut("Stopping web service")
                    webService.stop()
                } catch (e: Exception) {
                    // ignored
                }
            })
        }

    }

}

