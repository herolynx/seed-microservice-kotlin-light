package com.herolynx.service

import com.herolynx.service.monitoring.ProbesRestService

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
                        ProbesRestService()
                )
        )
        addShutdownHook(webServer)
    }

    internal fun addShutdownHook(webService: WebServer) {
        Runtime.getRuntime().addShutdownHook(Thread {
            try {
                System.out.println("********************************************")
                System.out.println("********    Stopping web service    ********")
                System.out.println("********************************************")
                webService.stop()
            } catch (e: Exception) {
                // ignored
            }
        })
    }

}

