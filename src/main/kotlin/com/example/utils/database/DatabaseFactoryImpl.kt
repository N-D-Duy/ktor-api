package com.example.utils.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import mu.two.KotlinLogging
import org.jetbrains.exposed.sql.*
import kotlin.time.Duration.Companion.milliseconds

val logger = KotlinLogging.logger {}

class DatabaseFactoryImpl(
    private val config: DatabaseConfig
): DatabaseFactory {
    override fun init() {
        logger.info { "Initializing database" }
        val url = config.url
        Database.connect(hikari(), databaseConfig = config())
        createTables()
        logger.info { "Connecting to $url" }
    }
    private fun config(): org.jetbrains.exposed.sql.DatabaseConfig = DatabaseConfig {
        sqlLogger = KotlinLoggingSqlLogger
        useNestedTransactions = true
        warnLongQueriesDuration = 50.milliseconds.inWholeMilliseconds
    }


    override fun createTables() {
        SchemaManager().createTables()
    }

    override fun dropTables() {
        SchemaManager().dropTables()
    }

    override fun clear() {
        SchemaManager().clear()
    }

    private fun hikari(): HikariDataSource{
        val config = HikariConfig().apply {
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            jdbcUrl = config.url
            driverClassName = config.driver
            username = config.user
            password = config.password
            maximumPoolSize = config.maxPoolSize
            addDataSourceProperty("rewriteBatchedStatements", true)
        }.also { it.validate() }
        return HikariDataSource(config)
    }
}

fun ApplicationEnvironment.dbConfig(path: String): DatabaseConfig = with(config.config(path)) {
    DatabaseConfig(
        driver = property("driver").getString(),
        url = property("url").getString(),
        user = property("username").getString(),
        password = property("pwd").getString(),
        maxPoolSize = property("maxPoolSize").getString().toInt()
    )
}