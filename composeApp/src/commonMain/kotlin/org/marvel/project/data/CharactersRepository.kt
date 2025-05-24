package org.marvel.project.data

interface CharactersRepository {
    suspend fun getCharacters(timestamp: Long, md5: String): List<Character>
}