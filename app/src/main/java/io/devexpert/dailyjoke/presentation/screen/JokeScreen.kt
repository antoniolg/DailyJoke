package io.devexpert.dailyjoke.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.devexpert.dailyjoke.data.api.NetworkModule
import io.devexpert.dailyjoke.data.repository.JokeRepositoryImpl
import io.devexpert.dailyjoke.domain.model.Joke
import io.devexpert.dailyjoke.presentation.viewmodel.JokeUiState
import io.devexpert.dailyjoke.presentation.viewmodel.JokeViewModel
import io.devexpert.dailyjoke.presentation.viewmodel.JokeViewModelFactory
import io.devexpert.dailyjoke.ui.theme.DailyJokeTheme

@Composable
fun JokeScreen(
    modifier: Modifier = Modifier
) {
    val repository = JokeRepositoryImpl(NetworkModule.jokeApiService)
    val viewModel: JokeViewModel = viewModel {
        JokeViewModelFactory(repository).create(JokeViewModel::class.java)
    }
    
    JokeScreen(
        uiState = viewModel.uiState.collectAsState().value,
        onNewJokeClick = { viewModel.loadRandomJoke() },
        onSaveFavoriteClick = { viewModel.saveFavoriteJoke() },
        modifier = modifier
    )
}

@Composable
fun JokeScreen(
    uiState: JokeUiState,
    onNewJokeClick: () -> Unit,
    onSaveFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showFavorites by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is JokeUiState.Loading -> {
                LoadingComponent()
            }
            is JokeUiState.Success -> {
                if (showFavorites) {
                    FavoritesContent(
                        favorites = uiState.favorites,
                        onBackClick = { showFavorites = false }
                    )
                } else {
                    JokeContent(
                        joke = uiState.joke,
                        favorites = uiState.favorites,
                        onNewJokeClick = onNewJokeClick,
                        onSaveFavoriteClick = onSaveFavoriteClick,
                        onShowFavoritesClick = { showFavorites = true }
                    )
                }
            }
            is JokeUiState.Error -> {
                ErrorComponent(
                    message = uiState.message,
                    onRetryClick = onNewJokeClick
                )
            }
        }
    }
}

@Composable
private fun LoadingComponent(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading a fresh joke...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun JokeContent(
    joke: Joke,
    favorites: List<Joke>,
    onNewJokeClick: () -> Unit,
    onSaveFavoriteClick: () -> Unit,
    onShowFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFavorite = favorites.contains(joke)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = joke.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (favorites.isNotEmpty()) {
                        OutlinedButton(
                            onClick = onShowFavoritesClick,
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                text = "❤️ ${favorites.size}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = joke.setup,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = joke.punchline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilledTonalButton(
                onClick = onSaveFavoriteClick,
                modifier = Modifier.weight(1f),
                enabled = !isFavorite
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Already favorited" else "Add to favorites"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isFavorite) "Favorited" else "Add to Favorites",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Button(
                onClick = onNewJokeClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "New Joke",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun FavoritesContent(
    favorites: List<Joke>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            
            Text(
                text = "Favorite Jokes (${favorites.size})",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (favorites.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "💔",
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No favorite jokes yet",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Save some jokes to see them here!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites) { joke ->
                    FavoriteJokeCard(joke = joke)
                }
            }
        }
    }
}

@Composable
private fun FavoriteJokeCard(
    joke: Joke,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = joke.category.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = joke.setup,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = joke.punchline,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ErrorComponent(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "😞",
            style = MaterialTheme.typography.displayMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Oops! Something went wrong",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedButton(
            onClick = onRetryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Try Again",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JokeScreenSuccessPreview() {
    DailyJokeTheme {
        JokeScreen(
            uiState = JokeUiState.Success(
                joke = Joke(
                    setup = "Why don't scientists trust atoms?",
                    punchline = "Because they make up everything!",
                    category = "Science"
                )
            ),
            onNewJokeClick = {},
            onSaveFavoriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JokeScreenWithFavoritesPreview() {
    DailyJokeTheme {
        JokeScreen(
            uiState = JokeUiState.Success(
                joke = Joke(
                    setup = "Why don't scientists trust atoms?",
                    punchline = "Because they make up everything!",
                    category = "Science"
                ),
                favorites = listOf(
                    Joke("Setup 1", "Punchline 1", "Category 1"),
                    Joke("Setup 2", "Punchline 2", "Category 2")
                )
            ),
            onNewJokeClick = {},
            onSaveFavoriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JokeScreenLoadingPreview() {
    DailyJokeTheme {
        JokeScreen(
            uiState = JokeUiState.Loading,
            onNewJokeClick = {},
            onSaveFavoriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JokeScreenErrorPreview() {
    DailyJokeTheme {
        JokeScreen(
            uiState = JokeUiState.Error("Network connection failed. Please check your internet connection."),
            onNewJokeClick = {},
            onSaveFavoriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesContentPreview() {
    DailyJokeTheme {
        FavoritesContent(
            favorites = listOf(
                Joke("Why don't scientists trust atoms?", "Because they make up everything!", "Science"),
                Joke("Why did the coffee file a police report?", "It got mugged!", "Pun"),
                Joke("What do you call a fake noodle?", "An Impasta!", "Food")
            ),
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesContentEmptyPreview() {
    DailyJokeTheme {
        FavoritesContent(
            favorites = emptyList(),
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JokeContentPreview() {
    DailyJokeTheme {
        JokeContent(
            joke = Joke(
                setup = "Why don't scientists trust atoms?",
                punchline = "Because they make up everything!",
                category = "Science"
            ),
            favorites = emptyList(),
            onNewJokeClick = {},
            onSaveFavoriteClick = {},
            onShowFavoritesClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JokeContentWithFavoritesPreview() {
    DailyJokeTheme {
        JokeContent(
            joke = Joke(
                setup = "Why don't scientists trust atoms?",
                punchline = "Because they make up everything!!!",
                category = "Science"
            ),
            favorites = listOf(
                Joke("Setup 1", "Punchline 1", "Category 1"),
                Joke("Setup 2", "Punchline 2", "Category 2")
            ),
            onNewJokeClick = {},
            onSaveFavoriteClick = {},
            onShowFavoritesClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingComponentPreview() {
    DailyJokeTheme {
        LoadingComponent()
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorComponentPreview() {
    DailyJokeTheme {
        ErrorComponent(
            message = "Network connection failed. Please check your internet connection.",
            onRetryClick = {}
        )
    }
}