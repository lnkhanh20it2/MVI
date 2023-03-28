package com.example.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi.DataState
import com.example.mvi.GetPost
import com.example.mvi.PostSate
import com.example.mvi.UIComponent
import com.example.mvi.network.PostApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class PostViewModel : ViewModel(), ContainerHost<PostSate, UIComponent> {
    val getPost = GetPost(PostApi.providePostApi())
    override val container: Container<PostSate, UIComponent> = container(PostSate())
    fun getPosts() {
        intent {
            val posts = getPost.execute()
            posts.onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        reduce {
                            state.copy(progressbar = dataState.isLoading)
                        }
                    }
                    is DataState.Success -> {
                        reduce {
                            state.copy(posts = dataState.data)
                        }
                    }
                    is DataState.Error -> {
                        when (dataState.uiComponent) {
                            is UIComponent.Toast -> {
                               postSideEffect(UIComponent.Toast(dataState.uiComponent.text))
                            }
                        }
                    }
                }

            }.launchIn(viewModelScope)
        }
    }
}