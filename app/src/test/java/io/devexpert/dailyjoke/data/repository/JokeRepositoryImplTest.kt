package io.devexpert.dailyjoke.data.repository

import io.devexpert.dailyjoke.data.api.JokeApiService
import io.devexpert.dailyjoke.data.model.JokeResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class JokeRepositoryImplTest {
    
    @Test
    fun `getRandomJoke should return success when API call succeeds`() = runBlocking {
        // Given
        val mockApiService = MockJokeApiService()
        val repository = JokeRepositoryImpl(mockApiService)
        
        // When
        val result = repository.getRandomJoke()
        
        // Then
        assertTrue(result.isSuccess)
        val joke = result.getOrNull()
        assertEquals("Why don't scientists trust atoms?", joke?.setup)
        assertEquals("Because they make up everything!", joke?.punchline)
        assertEquals("Science", joke?.category)
    }
    
    @Test
    fun `getRandomJoke should return failure when API call fails`() = runBlocking {
        // Given
        val mockApiService = MockJokeApiService()
        mockApiService.shouldThrowException = true
        val repository = JokeRepositoryImpl(mockApiService)
        
        // When
        val result = repository.getRandomJoke()
        
        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Network connection failed") == true)
    }
    
    private class MockJokeApiService : JokeApiService {
        var shouldThrowException = false
        
        override suspend fun getRandomJoke(): JokeResponse {
            if (shouldThrowException) {
                throw IOException("Network error")
            }
            return JokeResponse(
                setup = "Why don't scientists trust atoms?",
                delivery = "Because they make up everything!",
                category = "Science",
                type = "twopart",
                id = 1
            )
        }
    }
}