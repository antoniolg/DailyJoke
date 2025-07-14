package io.devexpert.dailyjoke.data.model

import io.devexpert.dailyjoke.domain.model.Joke

fun JokeResponse.toDomainModel(): Joke {
    return Joke(
        setup = this.setup,
        punchline = this.delivery,
        category = this.category
    )
}