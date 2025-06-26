package org.marvel.project.domain.repository

import org.marvel.project.domain.model.Character

interface CharactersRepository {
    suspend fun getCharacters(timestamp: Long, md5: String): List<Character>
}