package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val name: String,
    val url: String,
    val description: String
)

@Serializable
data class ImageResponse(
    val id: Long,
    val image: Image
)

@Serializable
data class ImageList(
    val imageList: List<Image>,
    val imageNo: Int,
    val limit: Int,
    val offset: Long
)