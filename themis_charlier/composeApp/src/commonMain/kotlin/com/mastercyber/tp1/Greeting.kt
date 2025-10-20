package com.mastercyber.tp1

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.request.get
import io.ktor.utils.io.core.toByteArray

class Greeting {
    private val platform = getPlatform()

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun fetchPokemonName(): String? {
        val random = (1..1025).random()
        val response: Pokemon =
            client.get("https://tyradex.vercel.app/api/v1/pokemon/$random").body()
        client.close()
        return response.name?.fr ?: "Unknown"
    }

    suspend fun fetchPokemonSprite(): ByteArray? {
        val random = (1..1025).random()
        val response: Pokemon =
            client.get("https://tyradex.vercel.app/api/v1/pokemon/$random").body()

        if(response.sprites?.regular != null){
            val imageResponse: ByteArray = client.get(response.sprites.regular).body()
            client.close()
            return imageResponse
        }
        client.close()
        return null
    }

    suspend fun fetchPokemon(): Pair<String?, ByteArray?> {
        val random = (1..1025).random()
        val response: Pokemon =
            client.get("https://tyradex.vercel.app/api/v1/pokemon/$random").body()
        val name = response.name?.fr ?: "Unknown"
        val spriteUrl = response.sprites?.regular

        val sprite: ByteArray? = if (spriteUrl != null) {
            client.get(spriteUrl).body()
        } else {
            null
        }

        client.close()
        return Pair(name, sprite)
    }

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun guessPokemon (name : String?, guess: String) : Boolean {
        return name?.lowercase() == guess.lowercase()
    }
}