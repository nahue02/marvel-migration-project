package org.marvel.project

import androidx.compose.ui.window.ComposeUIViewController
import org.marvel.project.data.database.AppDriverFactory
import org.marvel.project.data.database.DatabaseModule
import org.marvel.project.data.database.CharacterDao
import org.marvel.project.ui.viewmodel.createCharactersViewModel

fun MainViewController() = ComposeUIViewController {
    val driverFactory = AppDriverFactory()
    val database = DatabaseModule(driverFactory).database
    val characterDao = CharacterDao(database)

    val viewModel = createCharactersViewModel(characterDao = characterDao)

    App(viewModel)
}