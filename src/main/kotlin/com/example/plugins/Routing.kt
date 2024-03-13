package com.example.plugins

import com.example.routes.imageRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        imageRoutes()
        /*// Static plugin. Try to access `/static/index.html`
        static {
            resources("static")
        }*/
    }
}
