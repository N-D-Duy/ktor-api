package com.example.utils.functions

fun urlConverter(name: String): String{
    return "http://127.0.0.1:8080/static-images/${name}.png"
}

fun pathRef(name: String): String{
    return "C:\\Users\\nguye\\IdeaProjects\\ktor-sample\\src\\main\\resources\\static\\static-images\\${name}.png"
}