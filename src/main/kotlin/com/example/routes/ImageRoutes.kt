package com.example.routes

import com.example.dto.Image
import com.example.dto.ImageResponse
import com.example.usecases.ImagesUseCase
import com.example.utils.functions.pathRef
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File
import java.io.IOException

fun Routing.imageRoutes(){
    getImages()
    getRandomImage()
    getImageByName()
    addImage()
}

private fun Routing.getImageByName() {
    val getImageByNameUseCase by inject<ImagesUseCase>()
    get("/images/{name}") {
        val name = call.parameters["name"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing name")
        val image: Result<ImageResponse?> = getImageByNameUseCase.getImageByName(name)
        if (image.isSuccess) {
            call.respond(image.getOrNull()!!)
        } else {
            call.respondText("Image not found", status = HttpStatusCode.NotFound)
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

private fun Routing.addImage(){
    val addImageUseCase by inject<ImagesUseCase>()
    post("/add-image") {
        val image = call.receive<Image>()
        val result = addImageUseCase.addImage(image)
        if (result.isSuccess) {
            //insert image to /static/static-images
            val bytes = image.url
            val filePath = pathRef(image.name)
            try {
                // Ghi dữ liệu của ảnh vào file
                val file = File(filePath)
                file.writeBytes(bytes)
                // Gửi phản hồi với mã trạng thái 201 (Created) và kết quả của yêu cầu
                call.respond(HttpStatusCode.Created, result.getOrNull()!!)
            } catch (e: IOException) {
                // Nếu ghi dữ liệu vào file bị lỗi, gửi phản hồi với mã trạng thái 500 (Internal Server Error) và thông báo lỗi tương ứng
                call.respond(HttpStatusCode.InternalServerError, "Error writing image data to file: ${e.message}")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Image not added")
        }
    }
}