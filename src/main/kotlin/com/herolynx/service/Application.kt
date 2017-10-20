package com.herolynx.service

import com.herolynx.service.monitoring.HealthProbesRestService

object Application {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        System.out.println("********************************************")
        System.out.println("********    Starting web service    ********")
        System.out.println("********************************************")
        val webServer = WebServer()
        val port = 8080
        webServer.start(
                port = port,
                restControllers = arrayOf(
                        HealthProbesRestService()
                )
        )
        addShutdownHook(webServer)
    }

    internal fun addShutdownHook(webService: WebServer) {
        Runtime.getRuntime().addShutdownHook(Thread {
            try {
                webService.stop()
            } catch (e: Exception) {
                // ignored
            }
        })
    }

}

