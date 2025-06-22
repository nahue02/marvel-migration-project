package org.marvel.project.ui

import org.marvel.project.data.local.Character

sealed class ScreenState {

    object Loading : ScreenState()

    class ShowCharacters(val list: List<Character>) : ScreenState()
}