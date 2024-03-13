package com.example.orm.functions

import com.example.dto.Image
import com.example.dto.ImageList
import com.example.orm.ImageEntity
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.fromResultRowImage(): Image = Image(
    name = this[ImageEntity.name],
    url = this[ImageEntity.url],
    description = this[ImageEntity.description]
)

fun List<Image>.toImageList(limit: Int, offset: Long) = ImageList(
    imageList = this,
    imageNo = this.size,
    limit = limit,
    offset = offset
)