package org.marvel.project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.marvel.project.data.local.CharacterDao
import org.marvel.project.data.network.provideCharacterService
import org.marvel.project.data.network.provideHttpClient

class CharactersViewModelFactory(private val characterDao: CharacterDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val client = provideHttpClient()
        val charactersService = provideCharacterService(client, characterDao)
        return CharactersViewModel(charactersService) as T
    }
}