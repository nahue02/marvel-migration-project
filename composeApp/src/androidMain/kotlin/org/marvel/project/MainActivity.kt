package org.marvel.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModelProvider
import org.marvel.project.ui.viewmodel.CharactersViewModel
import org.marvel.project.ui.viewmodel.CharactersViewModelFactory
import org.marvel.project.data.database.AppDriverFactory
import org.marvel.project.data.database.DatabaseModule
import org.marvel.project.data.local.CharacterDao

class MainActivity : ComponentActivity() {
    //enableEdgeToEdge()
    private lateinit var viewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val driverFactory = AppDriverFactory(this)
        val database = DatabaseModule(driverFactory).database
        val characterDao = CharacterDao(database)

        viewModel =
            ViewModelProvider(this, CharactersViewModelFactory(characterDao))[CharactersViewModel::class.java]

        setContent {
            MaterialTheme {
                App(viewModel)
            }
        }
    }

}