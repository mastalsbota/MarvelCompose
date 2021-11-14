package com.mastalsbota.marvelcompose.data.network

import com.mastalsbota.marvelcompose.data.network.entities.ApiEvent
import com.mastalsbota.marvelcompose.data.network.entities.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsService {
    @GET("/v1/public/events")
    suspend fun getEvents(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ApiResponse<ApiEvent>

    @GET("/v1/public/comics/{eventId}")
    suspend fun findEvent(
        @Query("eventId") eventId: Int
    ): ApiResponse<ApiEvent>
}