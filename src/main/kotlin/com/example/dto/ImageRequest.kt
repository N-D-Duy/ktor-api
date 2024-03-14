package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val name: String,
    val url: ByteArray,
    val description: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (name != other.name) return false
        if (!url.contentEquals(other.url)) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + url.contentHashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}

@Serializable
data class ImageResponse(
    val id: Long,
    val name: String,
    val url: String,
    val description: String
)

@Serializable
data class ImageList(
    val imageList: List<Image>,
    val imageNo: Int,
    val limit: Int,
    val offset: Long
)