package org.marvel.project.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.marvel.project.domain.PublicKeyInterceptor
import org.marvel.project.domain.CharactersService
import org.marvel.project.data.KtorCharactersRepository
import org.marvel.project.data.KtorMarvelCharactersClient
import org.marvel.project.data.database.AppDriverFactory
import org.marvel.project.data.database.DatabaseModule
import org.marvel.project.data.local.CharacterDao

class CharactersViewModelFactory(private val characterDao: CharacterDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val apiClient = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(DefaultRequest) {
                header(HttpHeaders.Accept, "application/json")
                url("https://gateway.marvel.com/")
            }
            engine {
                config {
                    addInterceptor(PublicKeyInterceptor())
                }
            }
        }

        val ktorCharactersClient = KtorMarvelCharactersClient(apiClient)
        val charactersApi = KtorCharactersRepository(ktorCharactersClient, characterDao)
        val charactersService = CharactersService(charactersApi)
        return CharactersViewModel(charactersService, characterDao) as T
    }
}