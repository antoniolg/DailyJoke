package io.devexpert.dailyjoke

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import io.devexpert.dailyjoke.data.api.NetworkModule
import io.devexpert.dailyjoke.data.repository.JokeRepositoryImpl
import io.devexpert.dailyjoke.presentation.screen.JokeScreen
import io.devexpert.dailyjoke.presentation.viewmodel.JokeViewModel
import io.devexpert.dailyjoke.presentation.viewmodel.JokeViewModelFactory
import io.devexpert.dailyjoke.ui.theme.DailyJokeTheme

class MainActivity : ComponentActivity() {
    
    private lateinit var viewModel: JokeViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Setup dependencies
        val repository = JokeRepositoryImpl(NetworkModule.jokeApiService)
        val viewModelFactory = JokeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[JokeViewModel::class.java]
        
        setContent {
            DailyJokeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    JokeScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}