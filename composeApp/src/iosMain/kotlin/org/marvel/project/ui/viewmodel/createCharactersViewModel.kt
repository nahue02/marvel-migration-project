package org.marvel.project.ui.viewmodel

import io.ktor.client.HttpClient
import org.marvel.project.data.database.CharacterDao
import org.marvel.project.data.network.provideCharacterService
import org.marvel.project.data.network.provideHttpClient

fun createCharactersViewModel(client: HttpClient = provideHttpClient(), characterDao: CharacterDao): CharactersViewModel {
    val charactersService = provideCharacterService(client, characterDao)
    return CharactersViewModel(charactersService)
}