package org.marvel.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import org.marvel.project.databinding.ActivityMainBinding
import org.marvel.project.ui.characters.CharactersViewModel
import org.marvel.project.ui.characters.CharactersViewModelFactory
import org.marvel.project.ui.characters.ScreenState
import org.marvel.project.ui.components.VerticalSpaceItemDecoration
import kotlinx.coroutines.launch
import org.marvel.project.data.database.AppDriverFactory
import org.marvel.project.data.database.DatabaseModule
import org.marvel.project.data.local.Character
import org.marvel.project.data.local.CharacterDao
import org.marvel.project.ui.components.CharacterCard

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
                CharactersScreen(viewModel)
            }
        }
    }

}

@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    val screenState by viewModel.screenState.collectAsState(initial = ScreenState.Loading)
    when (screenState) {
        is ScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ScreenState.ShowCharacters -> {
            val characters = (screenState as ScreenState.ShowCharacters).list
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(characters) { character ->
                    CharacterCard(
                        character.name,
                        character.description,
                        character.thumbnailUrl
                    )
                }
            }
        }

    }

}