package org.marvel.project.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.marvel.project.domain.MarvelCharactersClient
import org.marvel.project.domain.PublicKeyInterceptor
import org.marvel.project.domain.RetrofitCharactersRepository
import org.marvel.project.domain.CharactersService
import okhttp3.OkHttpClient
import org.marvel.project.domain.KtorCharactersRepository
import org.marvel.project.domain.KtorMarvelCharactersClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharactersViewModelFactory : ViewModelProvider.Factory {
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
        val charactersApi = KtorCharactersRepository(ktorCharactersClient)
        val charactersService = CharactersService(charactersApi)
        return CharactersViewModel(charactersService) as T
    }
    /*
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(PublicKeyInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiClient = retrofit.create(MarvelCharactersClient::class.java)

        val charactersApi = RetrofitCharactersRepository(apiClient)
        val charactersService = CharactersService(charactersApi)
        return CharactersViewModel(charactersService) as T
    }
     */
}