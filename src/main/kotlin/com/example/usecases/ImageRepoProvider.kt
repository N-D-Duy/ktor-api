package com.example.usecases

import com.example.dto.Image
import com.example.dto.ImageResponse
import com.example.repositories.ImageRepository

interface ImageRepoProvider {
    suspend fun getRandomImage(): Result<ImageResponse?>
    suspend fun getImages(): Result<List<ImageResponse>>
    suspend fun getImage(name: String): Result<ImageResponse?>
    suspend fun addImage(image: Image): Result<ImageResponse?>
    suspend fun deleteImage(name: String): Result<ImageResponse?>
    suspend fun updateImage(name: String, url: String, description: String): Result<String>
}

class ImageRepoProviderImpl(
    private val imageRepository: ImageRepository
) : ImageRepoProvider {
    override suspend fun getRandomImage(): Result<ImageResponse?> {
        return imageRepository.getRandomImage()
    }

    override suspend fun getImages(): Result<List<ImageResponse>> {
        return imageRepository.getImages()
    }

    override suspend fun getImage(name: String): Result<ImageResponse?> {
        return imageRepository.getImage(name)
    }

    override suspend fun addImage(image: Image): Result<ImageResponse?> {
        return imageRepository.addImage(image)
    }

    override suspend fun deleteImage(name: String): Result<ImageResponse?> {
        return imageRepository.deleteImage(name)
    }

    override suspend fun updateImage(name: String, url: String, description: String): Result<String> {
        return imageRepository.updateImage(name, url, description)
    }
}