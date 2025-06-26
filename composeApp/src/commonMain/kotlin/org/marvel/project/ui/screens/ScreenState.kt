package org.marvel.project.ui.screens

import org.marvel.project.domain.model.Character

sealed class ScreenState {

    object Loading : ScreenState()

    class ShowCharacters(val list: List<Character>) : ScreenState()
}