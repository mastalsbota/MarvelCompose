package com.mastalsbota.marvelcompose.data

import com.mastalsbota.marvelcompose.data.entities.Character
import com.mastalsbota.marvelcompose.data.entities.Reference
import com.mastalsbota.marvelcompose.data.entities.Url
import com.mastalsbota.marvelcompose.data.network.ApiClient
import com.mastalsbota.marvelcompose.data.network.entities.ApiCharacter
import com.mastalsbota.marvelcompose.data.network.entities.asString

object CharactersRepository {

    private var charactersCache = emptyList<Character>()

    suspend fun getCharacters(): List<Character> {
        if (charactersCache.isEmpty()) {
            val response = ApiClient.charactersService.getCharacters(0, 100)
            charactersCache = response.data.results.map { it.asCharacter() }
        }
        return charactersCache
    }

    suspend fun findCharacter(characterId: Int): Character {
        val character = charactersCache.find { it.id == characterId }
        if (character != null) return character

        val response = ApiClient.charactersService.findCharacter(characterId)
        return response.data.results.first().asCharacter()
    }
}

private fun ApiCharacter.asCharacter(): Character {
    val comics = comics.items.map { Reference(it.name) }
    val events = events.items.map { Reference(it.name) }
    val series = series.items.map { Reference(it.name) }
    val stories = stories.items.map { Reference(it.name) }
    val urls = urls.map { Url(it.type, it.url) }
    return Character(
        id,
        name,
        description,
        thumbnail.asString(),
        comics,
        events,
        series,
        stories,
        urls
    )

}