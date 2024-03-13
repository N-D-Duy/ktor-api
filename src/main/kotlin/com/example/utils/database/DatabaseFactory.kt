package com.example.utils.database

interface DatabaseFactory {
    fun init()
    fun createTables()
    fun dropTables()
    fun clear()
}

data class DatabaseConfig(
    val driver: String,
    val url: String,
    val user: String,
    val password: String,
    val maxPoolSize: Int
)