package org.marvel.project.data.database

import org.marvel.project.Database

class DatabaseModule(driverFactory: AppDriverFactory) {
    private val driver = driverFactory.createDriver()
    val database = Database(driver)
}