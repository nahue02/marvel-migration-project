package org.marvel.project.data

import org.marvel.project.data.local.Character
import org.marvel.project.data.local.CharacterDao

class KtorCharactersRepository(
    private val apiClient: KtorMarvelCharactersClient,
    private val characterDao: CharacterDao
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