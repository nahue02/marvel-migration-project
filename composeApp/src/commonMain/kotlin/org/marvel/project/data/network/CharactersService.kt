package org.marvel.project.data.network

import com.soywiz.krypto.md5
import kotlinx.datetime.Clock
import org.marvel.project.data.PRIVATE_KEY
import org.marvel.project.data.PUBLIC_KEY
import org.marvel.project.domain.model.Character
import org.marvel.project.domain.repository.CharactersRepository
import org.marvel.project.data.database.CharacterDao

class CharactersService(
    private val charactersRepository: CharactersRepository,
    private val characterDao: CharacterDao
    ) {

    suspend fun getCharacters(): List<Character> {
        var characters: List<Character> = listOf()

        try {
            val timestamp = Clock.System.now().toEpochMilliseconds()
            val md5 = md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY)
            characters = charactersRepository.getCharacters(timestamp, md5)

            println("Successful connection to API...")
            characterDao.deleteAllCharacters()
            characters.forEach { characterDao.insertCharacter(it) }

        } catch (e: Exception) {
            println(e.message)
            println("Unsuccessful connection to API...")
            characters = characterDao.getAllCharacters()
        }

        return sort(characters)
    }

    private fun md5(string: String): String {
        return try {
            println("Calculating MD5...")
            val md5 = string.encodeToByteArray().md5().hex
            println("Done calculating MD5...")
            println(md5)
            md5

        } catch (e: Exception) {
            println("MD5 Exception: " + e.message)
            ""
        }
    }

    private fun sort(characters: List<Character>): List<Character> {
        return characters.sortedWith(CharacterComparator())
    }

    /**
     * Los personajes se ordenan de la siguiente manera:
     * - Primero los que tienen descripción, y luego los que no.
     * - Los que tienen descripción a su vez se ordenan ascendentemente por su id.
     * - Los que NO tienen descripción se ordenan descendentemente por su id.
     */
    private class CharacterComparator : Comparator<Character> {
        override fun compare(c1: Character, c2: Character): Int {
            if (c1.description.isEmpty() && c2.description.isEmpty()) {
                return c2.id.compareTo(c1.id)
            }
            if (c1.description.isNotEmpty() && c2.description.isNotEmpty()) {
                return c1.id.compareTo(c2.id)
            }
            if (c1.description.isNotEmpty() && c2.description.isEmpty()) {
                return -1
            }
            return 1
        }

    }
}