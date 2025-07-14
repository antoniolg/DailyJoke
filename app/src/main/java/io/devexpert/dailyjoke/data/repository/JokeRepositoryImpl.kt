package io.devexpert.dailyjoke.data.repository

import io.devexpert.dailyjoke.data.api.JokeApiService
import io.devexpert.dailyjoke.data.model.toDomainModel
import io.devexpert.dailyjoke.domain.model.Joke
import io.devexpert.dailyjoke.domain.repository.JokeRepository
import retrofit2.HttpException
import java.io.IOException

class JokeRepositoryImpl(
    private val apiService: JokeApiService
) : JokeRepository {
    
    override suspend fun getRandomJoke(): Result<Joke> {
        return try {
            val response = apiService.getRandomJoke()
            Result.success(response.toDomainModel())
        } catch (e: HttpException) {
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: IOException) {
            Result.failure(Exception("Network connection failed. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("An unexpected error occurred: ${e.message}"))
        }
    }
}