package com.herolynx.service

import com.herolynx.service.monitoring.error
import com.herolynx.service.monitoring.info
import com.typesafe.config.ConfigFactory
import org.funktionale.option.Option
import org.funktionale.option.getOrElse
import org.funktionale.tries.Try
import java.io.File

object ConfigLoader {

    private lateinit var instance: com.typesafe.config.Config

    fun load(path: String) = instance.getConfig(path)

    internal fun init(extProperties: Option<File> = Option.None) {
        info("Initializing configuration - config file: ${extProperties}")
        instance = ConfigFactory.load()
                .withFallback(ConfigFactory.systemProperties())
                .withFallback(
                        readConfig(extProperties)
                                .onFailure { ex -> error("Couldn't load configuration - config file: $extProperties", ex) }
                                .get()
                )
    }

    private fun readConfig(configFile: Option<File>) =
            configFile.map { file ->
                Try {
                    ConfigFactory.parseFile(file)
                }
            }
                    .getOrElse { (Try.Success(ConfigFactory.empty())) }

}