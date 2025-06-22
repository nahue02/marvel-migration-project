package org.marvel.project.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.marvel.project.Database

actual class AppDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
         return AndroidSqliteDriver(Database.Schema, context, "app.db")
    }
}