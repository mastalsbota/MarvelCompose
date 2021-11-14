package com.mastalsbota.marvelcompose.data.repositories

import com.mastalsbota.marvelcompose.data.entities.Event
import com.mastalsbota.marvelcompose.data.network.ApiClient

object EventsRepository : Repository<Event>() {

    suspend fun get(): List<Event> = super.get {
        ApiClient
            .eventsService
            .getEvents(0, 100)
            .data
            .results
            .map { it.asEvent() }
    }

    suspend fun find(id: Int): Event = super.find(
        id,
        findActionRemote = {
            ApiClient
                .eventsService
                .findEvent(id)
                .data
                .results
                .first()
                .asEvent()
        }
    )
}
