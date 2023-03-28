package com.example.mvi.network

import com.example.mvi.model.Post
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class PostApiImpl(private var httpClient: HttpClient) :PostApi{
    override suspend fun getPosts(): List<Post> {
        return httpClient.get{
            url("https://jsonplaceholder.typicode.com/posts")
        }.body()
    }
}