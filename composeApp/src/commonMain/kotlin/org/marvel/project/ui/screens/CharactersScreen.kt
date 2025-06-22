package org.marvel.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.marvel.project.ui.components.CharacterCard
import org.marvel.project.ui.viewmodel.CharactersViewModel

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