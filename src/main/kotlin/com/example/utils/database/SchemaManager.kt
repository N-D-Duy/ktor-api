package com.example.utils.database

import com.example.orm.ImageEntity
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class SchemaManager {
    private val tables: Array<Table> = arrayOf(
        ImageEntity
    )

    fun createTables() {
        transaction {
            SchemaUtils.create(*tables)
        }
    }
    fun dropTables() {
        transaction {
            SchemaUtils.drop(*tables)
        }
    }
    fun clear() {
        transaction {
            for (table in tables) {
                table.deleteAll()
            }
        }
    }
}