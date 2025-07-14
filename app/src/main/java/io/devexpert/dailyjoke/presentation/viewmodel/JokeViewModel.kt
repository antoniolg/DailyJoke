package io.devexpert.dailyjoke.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.devexpert.dailyjoke.domain.model.Joke
import io.devexpert.dailyjoke.domain.repository.JokeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JokeViewModel(
    private val repository: JokeRepository
) : ViewModel() {
    
    private val _favorites = MutableStateFlow<List<Joke>>(emptyList())
    
    private val _uiState = MutableStateFlow<JokeUiState>(JokeUiState.Loading)
    val uiState: StateFlow<JokeUiState> = _uiState.asStateFlow()
    
    init {
        loadRandomJoke()
    }
    
    fun loadRandomJoke() {
        viewModelScope.launch {
            _uiState.value = JokeUiState.Loading
            
            repository.getRandomJoke()
                .onSuccess { joke ->
                    _uiState.value = JokeUiState.Success(joke, _favorites.value)
                }
                .onFailure { error ->
                    _uiState.value = JokeUiState.Error(
                        error.message ?: "Unknown error occurred"
                    )
                }
        }
    }
    
    fun saveFavoriteJoke() {
        val currentState = _uiState.value
        if (currentState is JokeUiState.Success) {
            val currentJoke = currentState.joke
            val currentFavorites = _favorites.value
            
            // Avoid duplicates
            if (!currentFavorites.contains(currentJoke)) {
                val updatedFavorites = currentFavorites + currentJoke
                _favorites.value = updatedFavorites
                _uiState.value = JokeUiState.Success(currentJoke, updatedFavorites)
            }
        }
    }
}