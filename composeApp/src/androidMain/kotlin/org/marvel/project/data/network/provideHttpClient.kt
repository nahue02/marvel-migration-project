package org.marvel.project.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

fun provideHttpClient(): HttpClient {
    return createHttpClient(OkHttp.create())
}