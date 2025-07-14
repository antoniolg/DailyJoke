package io.devexpert.dailyjoke.domain.repository

import io.devexpert.dailyjoke.domain.model.Joke

interface JokeRepository {
    suspend fun getRandomJoke(): Result<Joke>
}