package org.marvel.project.domain.model

data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnailUrl: String
)
