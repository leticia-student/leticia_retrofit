package com.example.retrofit_radier

import com.example.retrofit_radier.network.Comment
import com.example.retrofit_radier.network.Post

sealed interface PostDetailUiState {
    data object Loading : PostDetailUiState
    data class Success(val post: Post, val comments: List<Comment>) : PostDetailUiState
    data class Error(val message: String) : PostDetailUiState
}
