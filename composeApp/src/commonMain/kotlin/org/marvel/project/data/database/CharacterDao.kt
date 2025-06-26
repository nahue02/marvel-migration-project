package org.marvel.project.data.database

import org.marvel.project.CharacterQueries
import org.marvel.project.Database
import org.marvel.project.domain.model.Character

class CharacterDao(database: Database) {
    private val characterQueries: CharacterQueries = database.characterQueries

    fun insertCharacter(character: Character) {
        println("CharacterDao - Inserting character: $character")
        characterQueries.insert(character.id, character.name, character.description, character.thumbnailUrl)
    }

    fun getAllCharacters(): List<Character> {
        println("Getting all characters from local...")
        return characterQueries.selectAll().executeAsList().map {
            Character(
                id = it.id,
                name = it.name,
                description = it.description.toString(),
                thumbnailUrl = it.thumbailUrl.toString()
            )
        }
    }

    fun deleteAllCharacters() {
        println("Deleting all characters...")
        characterQueries.deleteAll()
    }
}