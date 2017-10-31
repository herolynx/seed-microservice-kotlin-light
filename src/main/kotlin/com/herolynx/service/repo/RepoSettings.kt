package com.herolynx.service.repo

import com.herolynx.service.ConfigLoader
import com.typesafe.config.Config

class RepoSettings(private val config: Config) {

    fun getUrl() = config.getString("url")

    companion object {

        fun create(config: Config = ConfigLoader.load("db")): RepoSettings = RepoSettings(config)

    }

}