package com.example.orm

import org.jetbrains.exposed.dao.id.LongIdTable

object ImageEntity: LongIdTable(name = "images"){
    val name = varchar("name", 255)
    val url = varchar("url", 255)
    val description = varchar("description", 255)
}