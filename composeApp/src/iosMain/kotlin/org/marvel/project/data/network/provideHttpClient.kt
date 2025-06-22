package org.marvel.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

fun provideHttpClient(): HttpClient {
    return createHttpClient(Darwin.create())
}