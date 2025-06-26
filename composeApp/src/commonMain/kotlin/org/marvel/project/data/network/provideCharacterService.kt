package org.marvel.project.data.network

import io.ktor.client.HttpClient
import org.marvel.project.data.repository.KtorCharactersRepository
import org.marvel.project.data.database.CharacterDao

fun provideCharacterService(httpClient: HttpClient, characterDao: CharacterDao): CharactersService {
    val ktorCharactersClient = KtorMarvelCharactersClient(httpClient)
    val characterRepository = KtorCharactersRepository(ktorCharactersClient)
    return CharactersService(characterRepository, characterDao)
}