package org.marvel.project.data.database

import app.cash.sqldelight.db.SqlDriver

expect class AppDriverFactory{
    fun createDriver(): SqlDriver
}

