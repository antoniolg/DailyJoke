package io.devexpert.dailyjoke.presentation.viewmodel

import io.devexpert.dailyjoke.domain.model.Joke

sealed class JokeUiState {
    object Loading : JokeUiState()
    data class Success(val joke: Joke) : JokeUiState()
    data class Error(val message: String) : JokeUiState()
}