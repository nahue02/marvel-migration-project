package org.marvel.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform