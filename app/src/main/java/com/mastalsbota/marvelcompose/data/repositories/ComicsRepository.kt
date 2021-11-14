package com.mastalsbota.marvelcompose.data.repositories

import com.mastalsbota.marvelcompose.data.entities.Comic
import com.mastalsbota.marvelcompose.data.network.ApiClient

object ComicsRepository : Repository<Comic>() {

    suspend fun get(format: Comic.Format? = null): List<Comic> = super.get {
        ApiClient
            .comicsService
            .getComics(0, 100, format?.toStringFormat())
            .data
            .results
            .map { it.asComic() }
    }

    suspend fun find(id: Int): Comic = super.find(
        id = id,
        findActionRemote = {
            ApiClient
                .comicsService
                .findComic(id)
                .data
                .results
                .first()
                .asComic()
        }
    )
}