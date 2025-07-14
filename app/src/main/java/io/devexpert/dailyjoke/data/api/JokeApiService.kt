package io.devexpert.dailyjoke.data.api

import io.devexpert.dailyjoke.data.model.JokeResponse
import retrofit2.http.GET

interface JokeApiService {
    @GET("joke/Any?type=twopart")
    suspend fun getRandomJoke(): JokeResponse
}