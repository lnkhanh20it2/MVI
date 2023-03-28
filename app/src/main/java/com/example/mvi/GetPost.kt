package com.example.mvi

import com.example.mvi.model.Post
import com.example.mvi.network.PostApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPost(private val postApi: PostApi) {
    fun execute(): Flow<DataState<List<Post>>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                val posts = postApi.getPosts()
                emit(DataState.Success(posts))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(UIComponent.Toast(e.message ?: "Unknown error")))
            } finally {
                emit(DataState.Loading(false))
            }
        }
    }
}
