package com.example.retrofit_radier

import com.example.retrofit_radier.network.Post

sealed interface PostUiState {
    data object Loading : PostUiState
    data class Success(val posts: List<Post>) : PostUiState
    data class Error(val message: String) : PostUiState
}
