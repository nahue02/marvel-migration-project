package org.marvel.project.data.repository

import org.marvel.project.data.network.CharactersResponse
import org.marvel.project.data.network.KtorMarvelCharactersClient
import org.marvel.project.domain.model.Character
import org.marvel.project.domain.repository.CharactersRepository

class KtorCharactersRepository(
    private val apiClient: KtorMarvelCharactersClient,
    ) :
    CharactersRepository {

    override suspend fun getCharacters(timestamp: Long, md5: String): List<Character> {
        val characters = apiClient.getAllCharacters(timestamp, md5).toModel()
        return characters
    }

    private fun CharactersResponse.toModel(): List<Character> {
        return this.characters.list.map {
            Character(
                id = it.id,
                name = it.name,
                description = it.description,
                thumbnailUrl = it.thumbnail.toUrl()
            )
        }
    }
}