package com.example.repositories

import com.example.dto.Image
import com.example.dto.ImageResponse
import com.example.orm.ImageEntity
import com.example.orm.ImageEntity.description
import com.example.utils.database.DbTransaction
import com.example.utils.resultOf
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.selectAll

interface ImageRepository {
    suspend fun getRandomImage(): Result<ImageResponse?>
    suspend fun getImages(): Result<List<ImageResponse>>
    suspend fun getImage(name: String): Result<ImageResponse?>
    suspend fun addImage(name: String, url: String, description: String): Result<ImageResponse?>
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
                        image = Image(
                            name = it[ImageEntity.name],
                            url = it[ImageEntity.url],
                            description = it[description]
                        )
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
                        image = Image(
                            name = it[ImageEntity.name],
                            url = it[ImageEntity.url],
                            description = it[description]
                        )
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
                        image = Image(
                            name = it[ImageEntity.name],
                            url = it[ImageEntity.url],
                            description = it[description]
                        )
                    )
                }.firstOrNull { it.image.name == name }
                image
            }
        }
    }

    override suspend fun addImage(name: String, url: String, description: String): Result<ImageResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                val id = ImageEntity.insertIgnoreAndGetId {
                    it[ImageEntity.name] = name
                    it[ImageEntity.url] = url
                    it[ImageEntity.description] = description
                }
                id?.let {
                    ImageResponse(
                        id = it.value,
                        image = Image(
                            name = name,
                            url = url,
                            description = description
                        )
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