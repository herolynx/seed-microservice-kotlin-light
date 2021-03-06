package com.herolynx.service

import com.herolynx.service.server.WebServer
import org.funktionale.option.Option
import org.funktionale.option.getOrElse
import org.funktionale.option.toOption
import java.io.File

object Application {

    private fun consoleOut(msg: String) {
        System.out.println("********************************************")
        System.out.println("********    $msg")
        System.out.println("********************************************")
    }

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val configFile = System.getProperty("config.file", "./application.conf").toOption().map { path -> File(path) }
        consoleOut("Starting web service - config file: $configFile")
        ConfigLoader.init(configFile)
        val webServer = WebServer()
        val port = 8080
        val pkgName = javaClass.`package`.name;
        webServer.start(rootPkgName = pkgName, port = port)
        addShutdownHook(webServer)
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

