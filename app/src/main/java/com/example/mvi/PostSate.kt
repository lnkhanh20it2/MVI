package com.example.mvi

import com.example.mvi.model.Post

data class PostSate(
    val progressbar: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String? = null
)