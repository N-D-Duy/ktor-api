package com.example.usecases

import com.example.dto.Image
import com.example.dto.ImageResponse
import com.example.repositories.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface ImagesUseCase {
    suspend fun getImages(): Result<List<ImageResponse>>
    suspend fun getRandomImage(): Result<ImageResponse?>
    suspend fun getImageByName(name: String): Result<ImageResponse?>

    suspend fun addImage(image: Image): Result<ImageResponse?>
}

class ImagesUseCasesImpl(
    private val dispatcher: CoroutineDispatcher,
    private val imageRepository: ImageRepository
) : ImagesUseCase {
    override suspend fun getImages(): Result<List<ImageResponse>> = withContext(dispatcher) {
        imageRepository.getImages()
    }

    override suspend fun getRandomImage(): Result<ImageResponse?> = withContext(dispatcher) {
        imageRepository.getRandomImage()
    }

    override suspend fun getImageByName(name: String): Result<ImageResponse?> = withContext(dispatcher) {
        imageRepository.getImage(name)
    }

    override suspend fun addImage(image: Image): Result<ImageResponse?> {
        return imageRepository.addImage(image)
    }
}

