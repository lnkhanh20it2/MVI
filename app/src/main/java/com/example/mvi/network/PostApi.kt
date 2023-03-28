package com.example.mvi.network

import com.example.mvi.model.Post
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface PostApi {
    suspend fun getPosts():List<Post>

    companion object{
        val httpClient = HttpClient(Android){
            install(ContentNegotiation){
                json(
                    Json{
                        this.ignoreUnknownKeys = true
                    }
                )
            }
        }
        fun providePostApi():PostApi = PostApiImpl(httpClient)
    }
}