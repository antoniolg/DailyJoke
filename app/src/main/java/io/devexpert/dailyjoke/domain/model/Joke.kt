package io.devexpert.dailyjoke.domain.model

data class Joke(
    val setup: String,
    val punchline: String,
    val category: String
)