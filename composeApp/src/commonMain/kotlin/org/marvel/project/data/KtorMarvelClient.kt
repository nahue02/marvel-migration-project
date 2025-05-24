package org.marvel.project.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


class KtorMarvelCharactersClient(private val client: HttpClient) {
    suspend fun getAllCharacters(timestamp: Long, md5: String): CharactersResponse {
        return client.get("v1/public/characters") {
            parameter("ts", timestamp)
            parameter("hash", md5)
        }.body()
    }
}


@Serializable
data class CharactersResponse(
    @SerialName("data") val characters: CharacterData
)

@Serializable
data class CharacterData(
    @SerialName("results")
    val list: List<CharacterResult>
)

@Serializable
data class CharacterResult(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("thumbnail") val thumbnail: Thumbnail
)

@Serializable
data class Thumbnail(
    @SerialName("path") val path: String,
    @SerialName("extension") val extension: String
) {
    fun toUrl() : String {
        return "$path.$extension"
    }
}
