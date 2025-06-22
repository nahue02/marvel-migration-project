package org.marvel.project.data

import org.marvel.project.data.local.Character

interface CharactersRepository {
    suspend fun getCharacters(timestamp: Long, md5: String): List<Character>
}