package com.example

import com.example.database.ConnectionParams
import com.example.database.DatabaseFactory
import com.example.plugins.configureRouting
import com.example.plugins.configureSockets
import io.ktor.server.application.*
import io.ktor.server.config.yaml.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main() {
    val parallelism = 4
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module, configure = {
        callGroupSize = parallelism
        connectionGroupSize = parallelism / 2 + 1
        workerGroupSize = parallelism / 2 + 1
    })
        .start(wait = true)
}

fun Application.module() {

    val config = YamlConfig("application.yaml")
    val url = config!!.propertyOrNull("storage.jdbcURL")?.getString()!!
    val user = config.propertyOrNull("storage.user")?.getString()!!
    val password = config.propertyOrNull("storage.password")?.getString()!!

    DatabaseFactory.init(
        ConnectionParams(
            url, user, password
        )
    )
    configureSockets()
    configureRouting()
    install(CORS) {
        anyHost()
    }
}
