package com.example.plugins

import com.example.routes.imageRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        imageRoutes()
        // Static plugin. Try to access `/static/index.html`
        static {
            resources("static")
        }
        get("/") {
            call.respondText(
                this::class.java.classLoader.getResource("static/index.html")!!.readText(),
                ContentType.Text.Html
            )
        }
    }
}
