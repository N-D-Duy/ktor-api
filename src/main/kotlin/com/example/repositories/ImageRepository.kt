package com.example.repositories

import com.example.dto.Image
import com.example.dto.ImageResponse
import com.example.orm.ImageEntity
import com.example.orm.ImageEntity.description
import com.example.utils.database.DbTransaction
import com.example.utils.functions.urlConverter
import com.example.utils.resultOf
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.selectAll

interface ImageRepository {
    suspend fun getRandomImage(): Result<ImageResponse?>
    suspend fun getImages(): Result<List<ImageResponse>>
    suspend fun getImage(name: String): Result<ImageResponse?>
    suspend fun addImage(image: Image): Result<ImageResponse?>
    suspend fun deleteImage(name: String): Result<ImageResponse?>
    suspend fun updateImage(name: String, url: String, description: String): Result<String>
}

class ImageRepositoryImpl(
    private val dbTransaction: DbTransaction
) : ImageRepository {
    override suspend fun getRandomImage(): Result<ImageResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                val image = ImageEntity.selectAll().toList().shuffled().firstOrNull()
                image?.let {
                    ImageResponse(
                        id = it[ImageEntity.id].value,
                        name = it[ImageEntity.name],
                        url = it[ImageEntity.url],
                        description = it[description]
                    )
                }
            }
        }
    }

    override suspend fun getImages(): Result<List<ImageResponse>> {
        return dbTransaction.dbQuery {
            resultOf {
                ImageEntity.selectAll().map {
                    ImageResponse(
                        id = it[ImageEntity.id].value,
                        name = it[ImageEntity.name],
                        url = it[ImageEntity.url],
                        description = it[description]
                    )
                }
            }
        }
    }

    override suspend fun getImage(name: String): Result<ImageResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                val image = ImageEntity.selectAll().map {
                    ImageResponse(
                        id = it[ImageEntity.id].value,
                        name = it[ImageEntity.name],
                        url = it[ImageEntity.url],
                        description = it[description]
                    )
                }.firstOrNull { it.name == name }
                image
            }
        }
    }

    override suspend fun addImage(image: Image): Result<ImageResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                val id = ImageEntity.insertIgnoreAndGetId {
                    it[name] = image.name
                    it[url] = urlConverter(image.name)
                    it[description] = image.description
                }
                id?.let {
                    ImageResponse(
                        id = id.value,
                        name = image.name,
                        url = urlConverter(image.name),
                        description = image.description
                    )
                }
            }
        }
    }

    override suspend fun deleteImage(name: String): Result<ImageResponse?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateImage(name: String, url: String, description: String): Result<String> {
        TODO("Not yet implemented")
    }

}