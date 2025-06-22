package org.marvel.project.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.marvel.project.domain.CharactersService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.marvel.project.data.local.CharacterDao

class CharactersViewModel(private val charactersService: CharactersService, private val characterDao: CharacterDao) : ViewModel() {

    private val _screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)
    val screenState: Flow<ScreenState> = _screenState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("CharactersViewModel", "Error retrieving characters: ${throwable.message}")

        println("Unsuccessful connection to API...")
        val list = characterDao.getAllCharacters()
        _screenState.value = ScreenState.ShowCharacters(list)
    }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            val list = charactersService.getCharacters()

            println("Successful connection to API...")
            characterDao.deleteAllCharacters()
            list.forEach { characterDao.insertCharacter(it) }

            _screenState.value = ScreenState.ShowCharacters(list)
        }
    }

}