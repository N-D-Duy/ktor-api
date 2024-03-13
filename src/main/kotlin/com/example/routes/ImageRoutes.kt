package com.example.routes

import com.example.dto.ImageResponse
import com.example.usecases.ImagesUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.imageRoutes(){
    getImages()
    getRandomImage()
    getImageByName()
}

private fun Routing.getImageByName() {
    val getImageByNameUseCase by inject<ImagesUseCase>()
    get("/images/{name}") {
        val name = call.parameters["name"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing name")
        val image: Result<ImageResponse?> = getImageByNameUseCase.getImageByName(name)
        if (image.isSuccess) {
            call.respond(image.getOrNull()!!)
        } else {
            call.respond(HttpStatusCode.NotFound, "Image not found")
        }
    }
}

private fun Routing.getImages() {
    val getImagesUseCase by inject<ImagesUseCase>()
    get("/images") {
        val images = getImagesUseCase.getImages()
        if (images.isSuccess) {
            call.respond(images.getOrNull()!!)
        } else {
            call.respond(HttpStatusCode.NotFound, "Images not found")
        }
    }
}

private fun Routing.getRandomImage() {
    val getImagesUseCase by inject<ImagesUseCase>()
    get("/random-image") {
        val images: Result<List<ImageResponse>> = getImagesUseCase.getImages()
        if (images.isSuccess) {
            val randomImage = images.getOrNull()!!.random()
            call.respond(randomImage)
        } else {
            call.respond(HttpStatusCode.NotFound, "Images not found")
        }
    }
}