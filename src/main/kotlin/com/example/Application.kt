package com.example

import com.example.di.configure
import com.example.di.koinModules
import com.example.dto.Image
import com.example.plugins.configureMonitoring
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.utils.database.DatabaseFactory
import com.example.utils.database.dbConfig
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>): Unit = EngineMain.main(args)
@Suppress("unused") // Referenced in application.conf
fun Application.module(koinModules: List<Module> = koinModules()) {
    install(Koin) {
        configure(koinModules)
    }

    val dbFactory by inject<DatabaseFactory> { parametersOf(environment.dbConfig("ktor.database")) }

    dbFactory.init()

    configureSerialization()
    configureMonitoring()
    configureRouting()
}
